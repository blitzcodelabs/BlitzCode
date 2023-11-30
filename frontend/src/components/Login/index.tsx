"use client";

import { useForm } from "react-hook-form";
import Button from "../ui/Button";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import TextField from "../ui/TextField";
import ErrorSlot from "../ui/ErrorSlot";
import { useRouter } from "next/navigation";

const signUpSchema = z.object({
  email: z.string().email(),
  password: z.string().min(1),
});

type SignUpSchema = z.infer<typeof signUpSchema>;

const Login = () => {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
    reset,
  } = useForm<SignUpSchema>({ resolver: zodResolver(signUpSchema) });

  const { push } = useRouter();
  const onSubmit = async (data: SignUpSchema) => {
    const res = await fetch("http://localhost:8080/signin", {
      method: "POST",
      body: JSON.stringify({
        email: data.email,
        password: data.password
      }),
      headers: {
        "Content-Type": "application/json",
      }
    });
    if (res.ok) {
      console.log("got token: " + await res.text());
      push("/dashboard");
    } else {
      console.log(res);
    }
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
        {Object.values(errors).length > 0 && (
          <ErrorSlot className="text-center">
            <p>Your email or password is incorrect</p>
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
