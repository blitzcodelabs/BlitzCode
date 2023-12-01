"use client";

import { useForm } from "react-hook-form";
import Button from "../ui/Button";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import TextField from "../ui/TextField";
import ErrorSlot from "../ui/ErrorSlot";
import { useRouter } from "next/navigation";
import { login } from "@/lib/auth";

const loginSchema = z.object({
  email: z.string().email(),
  password: z.string().min(1, "Please enter a password"),
});

export type LoginSchema = z.infer<typeof loginSchema>;

const Login = () => {
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors, isSubmitting },
  } = useForm<LoginSchema>({ resolver: zodResolver(loginSchema) });

  const { push } = useRouter();
  const onSubmit = async (data: LoginSchema) => {
    if (await login(data)) push("dashboard");
    else setError("root", { message: "Incorrect email or password" });
  };

  return (
    <main className="flex justify-center">
      <img src="logo.svg" alt="logo" className="fixed top-64 max-w-md" />
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="h-screen w-fit flex flex-col justify-center items-center gap-32"
      >
        <h1>Sign In</h1>
        <div className="flex flex-col gap-8">
          <TextField {...register("email")} placeholder="Email" />
          <TextField
            {...register("password")}
            type="password"
            placeholder="Password"
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
        <Button disabled={isSubmitting} type="submit">
          Login
        </Button>
      </form>
    </main>
  );
};

export default Login;
