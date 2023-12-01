import { LoginSchema } from "@/components/Login";
import { SignUpSchema } from "@/components/SignUp";
import { post } from "./request";

export const getidToken = async () => {
    if (await refreshIfNeeded()) {
        return localStorage.getItem("idToken");
    }
    return null;
}

export const login = async (credentials: LoginSchema) => {
    const res = await post("/signin", JSON.stringify(credentials));
    if (res.ok) {
        const {idToken, refreshToken} = await res.json();
        updateTokens(idToken, refreshToken);
    }
    return res.ok;
}

export const signUp = async (credentials: SignUpSchema) => {
    const res = await post("/signup", JSON.stringify(credentials))
    if (res.ok) {
        const {idToken, refreshToken} = await res.json();
        updateTokens(idToken, refreshToken);
    }
    return res.ok;
}

const updateTokens = (idToken: string, refreshToken: string) => {
    localStorage.setItem("idToken", idToken);
    localStorage.setItem("refreshToken", refreshToken);
    // expires in 3600 seconds (60 second gap for safety)
    localStorage.setItem("expiry", (Date.now() + 3540 * 1000).toString());
}

const refreshIfNeeded = async () => {
    if (hasExpired()) {
        const refreshToken = localStorage.getItem("refreshToken");
        if (!refreshToken) {
            return false;
        }
        const res = await post("/refresh-token", refreshToken);
        if (!res.ok) {
            return false;
        }
        const {id_token, refresh_token} = await res.json();
        updateTokens(id_token, refresh_token);
    }
    return !hasExpired();
}

const hasExpired = () => {
    return Date.now() > Number(localStorage.getItem("expiry") || "0");
}
