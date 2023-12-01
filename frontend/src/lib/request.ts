import { getidToken } from "@/lib/auth";

const url = "http://localhost:8080"

const publicGetPaths = ["/languages"] as const;
const publicPostPaths = ["/signin", "/signup", "/refresh-token", "/send-reset-password-email"] as const;
const authGetPaths = ["/test", "/modules", "/lessons", "/account/baseLanguage", "/account/targetLanguage"] as const;
const authPostPaths = ["/account/baseLanguage", "/account/targetLanguage"] as const;
type PublicGetPath = typeof publicGetPaths[number];
type PublicPostPath = typeof publicPostPaths[number]
type AuthGetPath = typeof authGetPaths[number];
type AuthPostPath = typeof authPostPaths[number];

export const get = async (path: PublicGetPath) => {
    return await (await fetch(url + path)).json();
}

export const post = async (path: PublicPostPath, data: BodyInit) => {
    return await fetch(url + path, {
        method: "POST",
        body: data,
        headers: {
            "Content-Type": "application/json"
        }
    });
}

export const getWithAuth = async (path: AuthGetPath) => {
    const idToken = await getidToken();
    if (idToken) {
        return await fetch(url+ "/auth" + path, {
            headers: {
                "Authorization": "Bearer " + idToken
            }
        });
    }
    return null; // TODO: redirect to login page
}

export const postWithAuth = async (path: AuthPostPath, data: BodyInit) => {
    const idToken = await getidToken();
    if (idToken) {
        return await fetch(url + "/auth" + path, {
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
