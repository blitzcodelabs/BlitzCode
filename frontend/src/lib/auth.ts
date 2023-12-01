"use server";

import { LoginSchema } from "@/components/Login";
import { cookies } from "next/headers"
import { SignUpSchema } from "@/components/SignUp";
import { post } from "./request";

interface User {
    idToken: string;
    refreshToken: string;
}

export const login = async (credentials: LoginSchema) => {
    const res = await post("/signin", JSON.stringify(credentials));
    if (res.ok) {
        const data = await res.json();
        cookies().set("user", JSON.stringify(data));
    }
    return res.ok;
}

export const signUp = async (credentials: SignUpSchema) => {
    const res = await post("/signup", JSON.stringify(credentials))
    if (res.ok) {
        const data = await res.json();
        cookies().set("user", JSON.stringify(data));
    }
    return res.ok;
}

export const getUser = () => {
    const data = cookies().get("user")?.value;
    return data ? JSON.parse(data) as User : undefined;
}