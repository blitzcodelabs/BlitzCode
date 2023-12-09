"use client";

import React, { useEffect } from "react";
import { useForm } from "react-hook-form";
import Button from "../ui/Button";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import TextField from "../ui/TextField";
import ErrorSlot from "../ui/ErrorSlot";
import {useRouter, useSearchParams} from "next/navigation";
import { signUp } from "@/lib/auth";
import Link from "next/link";
import {postWithAuth} from "@/lib/request";

const signUpSchema = z
  .object({
    email: z.string().email(),
    password: z.string().min(8, "Password must be at least 8 characters"),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords must match",
    path: ["confirmPassword"],
  });

export type SignUpSchema = z.infer<typeof signUpSchema>;

const SignUp = () => {
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors, isSubmitting },
  } = useForm<SignUpSchema>({ resolver: zodResolver(signUpSchema) });

  const { push } = useRouter();
  const params = useSearchParams();
  const onSubmit = async (data: SignUpSchema) => {
    if (await signUp(data)) {
      const baseLanguage = params.get("baseLanguage");
      const targetLanguage = params.get("targetLanguage");
      if (baseLanguage)
        postWithAuth("/account/baseLanguage", JSON.stringify({id: baseLanguage.toUpperCase()} ));
      if (targetLanguage)
        postWithAuth("/account/targetLanguage", JSON.stringify({id: targetLanguage.toUpperCase()} ));
      push("dashboard");
    } else setError("root", { message: "Account already exists" });
  };

  return (
    <main className="flex justify-center">
      <img src="logo.svg" alt="logo" className="fixed top-64 max-w-md" />
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="h-screen w-fit flex flex-col justify-center items-center gap-32"
      >
        <h1>Sign Up</h1>
        <div className="flex flex-col gap-8">
          <TextField {...register("email")} placeholder="Email" autoComplete="username"/>
          <TextField
            {...register("password")}
            type="password"
            placeholder="Password"
            autoComplete="new-password"
          />
          <TextField
            {...register("confirmPassword")}
            type="password"
            placeholder="Confirm Password"
            autoComplete="new-password"
          />
        </div>

        {Object.values(errors).length !== 0 && (
          <ErrorSlot className="self-start">
            <ul className="list-disc list-inside">
              {Object.values(errors).map((error, index) => (
                <li key={index}>{error.message}</li>
              ))}
            </ul>
          </ErrorSlot>
        )}

        <div className="flex flex-col justify-center items-center gap-8">
          <Button intent={"accent"} disabled={isSubmitting} type="submit">
            create account
          </Button>
          <Button className="text-base" asChild>
            <Link href="login">i already have an account</Link>
          </Button>
        </div>
      </form>
    </main>
  );
};

export default SignUp;
