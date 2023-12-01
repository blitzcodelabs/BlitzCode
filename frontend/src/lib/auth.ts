"use server";

import { LoginSchema } from "@/components/Login";
import { cookies } from "next/headers"
import { SignUpSchema } from "@/components/SignUp";
import { post } from "./request";

export interface User {
    idToken: string;
    refreshToken: string;
    expiry: Date;
}

export const login = async (credentials: LoginSchema) => {
    const res = await post("/signin", JSON.stringify(credentials));
    if (res.ok) {
        const data = await res.json();
        updateUser(data.idToken, data.refreshToken);
    }
    return res.ok;
}

export const signUp = async (credentials: SignUpSchema) => {
    const res = await post("/signup", JSON.stringify(credentials))
    if (res.ok) {
        const data = await res.json();
        updateUser(data.idToken, data.refreshToken);
    }
    return res.ok;
}

export const refreshToken = async () => {
    const user = getUser();
    if (user) {
        const res = await post("/refresh-token", user.refreshToken);
        if (res.ok) {
            const {id_token, refresh_token} = await res.json();
            updateUser(id_token, refresh_token);
        }
        return res.ok;
    }
    return false;
}

export const getUser = () => {
    const data = cookies().get("user")?.value;
    return data ? JSON.parse(data) as User : undefined;
}

export const hasExpired = (user: User) => {
    return Date.now() > user.expiry.getTime();
}

export const refreshIfNeeded = async (user: User) => {
    if (hasExpired(user)) {
        return await refreshToken();
    }
    return true;
}

const updateUser = (idToken: string, refreshToken: string) => {
    const user: User = {
        idToken: idToken,
        refreshToken: refreshToken,
        // expires in 3600 seconds (100 second gap for safety)
        expiry: new Date(Date.now() + 3500 * 1000)
    };
    cookies().set("user", JSON.stringify(user));
}
