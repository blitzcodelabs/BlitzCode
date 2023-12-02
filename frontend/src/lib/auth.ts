import { LoginSchema } from "@/components/Login";
import { SignUpSchema } from "@/components/SignUp";
import { post } from "./request";

export const tryGetIDToken = async () => {
    const idToken = localStorage.getItem("idToken");
    if (!idToken) return;
    if (!hasExpired() || await tryRefresh()) return idToken;
}

export const login = async (credentials: LoginSchema) => {
    const res = await post("/signin", JSON.stringify(credentials));
    if (res.ok) {
        const { idToken, refreshToken } = await res.json();
        updateTokens(idToken, refreshToken);
    }
    return res.ok;
}

export const signUp = async (credentials: SignUpSchema) => {
    const res = await post("/signup", JSON.stringify(credentials))
    if (res.ok) {
        const { idToken, refreshToken } = await res.json();
        updateTokens(idToken, refreshToken);
    }
    return res.ok;
}

export const logout = () => localStorage.clear();

const updateTokens = (idToken: string, refreshToken: string) => {
    localStorage.setItem("idToken", idToken);
    localStorage.setItem("refreshToken", refreshToken);
    // expires in 3600 seconds (60 second gap for safety)
    localStorage.setItem("expiry", (Date.now() + 3540 * 1000).toString());
}

const hasExpired = () => {
    return Date.now() > Number(localStorage.getItem("expiry"));
}

const tryRefresh = async () => {
    const refreshToken = localStorage.getItem("refreshToken");
    if (!refreshToken) {
        return false;
    }

    const res = await post("/refresh-token", refreshToken);
    if (!res.ok) {
        return false;
    }

    const { id_token, refresh_token } = await res.json();
    updateTokens(id_token, refresh_token);
    return true;
}
