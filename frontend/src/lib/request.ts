import {refreshIfNeeded, getUser} from "@/lib/auth";

const url = "http://localhost:8080"

const publicGetPaths = ["/languages"] as const;
const publicPostPaths = ["/signin", "/signup", "/refresh-token", "/send-reset-password-email"] as const;
type GetPath = typeof publicGetPaths[number];
type PostPath = typeof publicPostPaths[number]

export const get = async (path: GetPath) => {
    return await (await fetch(url + path)).json();
}

export const post = async (path: PostPath, data: BodyInit) => {
    return await fetch(url + path, {
        method: "POST",
        body: data,
        headers: {
            "Content-Type": "application/json"
        }
    });
}

export const getWithAuth = async (path: GetPath) => {
    const user = getUser();
    if (user) {
        if (!await refreshIfNeeded(user)) {
            return undefined; // TODO: redirect to login page
        }
        return await fetch(url + path, {
            headers: {
                "Authorization": "Bearer " + user.idToken
            }
        });
    }
    // TODO: redirect to login page
    return undefined;
}

export const postWithAuth = async (path: PostPath, data: BodyInit) => {
    const user = getUser();
    if (user) {
        if (!await refreshIfNeeded(user)) {
            return undefined; // TODO: redirect to login page
        }
        return await fetch(url + path, {
            method: "POST",
            body: data,
            headers: {
                "Authorization": "Bearer " + user.idToken,
                "Content-Type": "application/json"
            }
        });
    }
    return undefined; // TODO: redirect to login page
}
