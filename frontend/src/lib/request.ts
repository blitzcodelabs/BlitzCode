import { getidToken } from "@/lib/auth";

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
    const idToken = await getidToken();
    if (idToken) {
        return await fetch(url + path, {
            headers: {
                "Authorization": "Bearer " + idToken
            }
        });
    }
    return null; // TODO: redirect to login page
}

export const postWithAuth = async (path: PostPath, data: BodyInit) => {
    const idToken = await getidToken();
    if (idToken) {
        return await fetch(url + path, {
            method: "POST",
            body: data,
            headers: {
                "Authorization": "Bearer " + idToken,
                "Content-Type": "application/json"
            }
        });
    }
    return null; // TODO: redirect to login page
}
