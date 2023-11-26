"use client";

import React, { useEffect } from "react";
import { useForm } from "react-hook-form";
import Button from "../ui/Button";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import TextField from "../ui/TextField";
import ErrorText from "../ui/ErrorText";
import { useRouter } from "next/navigation";

const signUpSchema = z
  .object({
    email: z.string().email(),
    password: z.string().min(10, "Password must be at least 10 characters"),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords must match.",
    path: ["confirmPassword"],
  });

type SignUpSchema = z.infer<typeof signUpSchema>;

const SignUp = () => {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting, isSubmitSuccessful },
    reset,
  } = useForm<SignUpSchema>({ resolver: zodResolver(signUpSchema) });

  const { push } = useRouter();
  const onSubmit = async (data: SignUpSchema) => {
    reset();
    await new Promise((resolve) => setTimeout(resolve, 1000));
    push("/dashboard");
  };

  useEffect(() => {
    reset();
  }, [isSubmitSuccessful, reset]);

  return (
    <main className="flex justify-center">
      <img src="logo.svg" alt="logo" className="fixed top-64 max-w-md" />
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="h-screen w-fit flex flex-col justify-center items-center gap-32"
      >
        <h1>Sign Up</h1>
        <div className="flex flex-col gap-8">
          <TextField {...register("email")} placeholder="Email" />
          <TextField
            {...register("password")}
            type="password"
            placeholder="Password"
          />
          <TextField
            {...register("confirmPassword")}
            type="password"
            placeholder="Confirm Password"
          />
        </div>

        {Object.values(errors).length !== 0 && (
          <ErrorText className="self-start">
            <ul className="list-disc list-inside">
              {Object.values(errors).map((error, index) => (
                <li key={index}>{error.message}</li>
              ))}
            </ul>
          </ErrorText>
        )}

        <Button disabled={isSubmitting} type="submit">
          create account
        </Button>
      </form>
    </main>
  );
};

export default SignUp;
