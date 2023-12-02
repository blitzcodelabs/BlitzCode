import { tryGetIDToken } from "@/lib/auth";

const url = "http://localhost:8080"

const publicGetPaths = ["/languages"] as const;
const authGetPaths = ["/test", "/modules", "/questions", "/account/baseLanguage", "/account/targetLanguage"] as const;
type PublicGetPath = typeof publicGetPaths[number];
type AuthGetPath = typeof authGetPaths[number];

const publicPostPaths = ["/signin", "/signup", "/refresh-token", "/send-reset-password-email"] as const;
const authPostPaths = ["/account/baseLanguage", "/account/targetLanguage"] as const;
type PublicPostPath = typeof publicPostPaths[number]
type AuthPostPath = typeof authPostPaths[number];


export const get = async <T>(path: PublicGetPath) => {
    return await (await fetch(url + path)).json() as T;
}

export const getWithAuth = async <T>(path: AuthGetPath) => {
    const idToken = tryGetIDToken();
    if (!idToken) return false;

    return await (await fetch(url + "/auth" + path, {
        headers: {
            "Authorization": "Bearer " + idToken
        }
    })).json() as T;
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

export const postWithAuth = async (path: AuthPostPath, data: BodyInit) => {
    const idToken = await tryGetIDToken();
    if (!idToken) return false;

    return await fetch(url + "/auth" + path, {
        method: "POST",
        body: data,
        headers: {
            "Authorization": "Bearer " + idToken,
            "Content-Type": "application/json"
        }
    });
}
