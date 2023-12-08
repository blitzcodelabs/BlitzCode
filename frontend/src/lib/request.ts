import { getidToken } from "@/lib/auth";

const publicGetPaths = ["/languages"] as const;
const publicPostPaths = ["/signin", "/signup", "/refresh-token", "/send-reset-password-email"] as const;
const authGetPaths = ["/test", "/modules", "/questions", "/account/baseLanguage", "/account/targetLanguage"] as const;
const authPostPaths = ["/questions/completed", "/account/baseLanguage", "/account/targetLanguage", "/account/resetemail"] as const;
type PublicGetPath = typeof publicGetPaths[number];
type PublicPostPath = typeof publicPostPaths[number]
type AuthGetPath = typeof authGetPaths[number];
type AuthPostPath = typeof authPostPaths[number];

type AuthDeletePath = "/account"

const getURL = () => {
    if (window.location.hostname.toLowerCase() === "blitzcode.org") {
        return "https://api.blitzcode.org";
    }
    return "http://localhost:8080";
}

export const get = async (path: PublicGetPath) => {
    return await (await fetch(getURL() + path)).json();
}

export const post = async (path: PublicPostPath, data: BodyInit) => {
    return await fetch(getURL() + path, {
        method: "POST",
        body: data,
        headers: {
            "Content-Type": "application/json"
        }
    });
}

export const getWithAuth = async (path: AuthGetPath, query?: string) => {
    const idToken = await getidToken();
    if (idToken) {
        return await fetch(`${getURL()}/auth${path}${query ? `/${query}` : ""}`, {
            headers: {
                "Authorization": "Bearer " + idToken
            }
        });
    }
    return null; // TODO: redirect to login page
}

export const postWithAuth = async (path: AuthPostPath, data: BodyInit, query?: string) => {
    const idToken = await getidToken();
    if (idToken) {
        return await fetch(`${getURL()}/auth${path}${query ? `/${query}` : ""}`, {
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

export const deleteWithAuth = async (path: AuthDeletePath) => {
    const idToken = await getidToken();
    if (idToken) {
        return await fetch(`${getURL()}/auth${path}`, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + idToken
            }
        });
    }
}
