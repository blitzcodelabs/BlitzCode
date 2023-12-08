"use client";

import { type SubmitHandler, useForm } from "react-hook-form";
import { z } from "zod";
import ErrorSlot from "../ui/ErrorSlot";
import TextField from "../ui/TextField";
import Button from "../ui/Button";
import { zodResolver } from "@hookform/resolvers/zod";
import { getidToken, logout } from "@/lib/auth";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { getWithAuth, post, postWithAuth } from "@/lib/request";

const changePasswordSchema = z.object({
  email: z.string().email(),
});

const changeEmailSchema = z.object({
  email: z.string().email(),
});

type ChangePasswordSchema = z.infer<typeof changePasswordSchema>;
type ChangeEmailSchema = z.infer<typeof changeEmailSchema>;

const Settings = () => {
  const changePasswordForm = useForm<ChangePasswordSchema>({
    resolver: zodResolver(changePasswordSchema),
  });
  const changePasswordError = changePasswordForm.formState.errors;
  const onSubmitChangePassword: SubmitHandler<ChangePasswordSchema> = (data) =>
    post("/send-reset-password-email", data.email);

  const changeEmailForm = useForm<ChangeEmailSchema>({
    resolver: zodResolver(changeEmailSchema),
  });
  const changeEmailError = changeEmailForm.formState.errors;
  const onSubmitChangeEmail: SubmitHandler<ChangeEmailSchema> = (data) =>
    postWithAuth("/account/resetemail", data.email);

  const [baseLanguage, setBaseLanguage] = useState<string | null>(null);
  useEffect(() => {
    getWithAuth("/account/baseLanguage")
      .then((res) => res?.text())
      .then((data) => {
        if (data) {
          setBaseLanguage(data);
        }
      });
  }, []);

  useEffect(() => {
    getidToken().then((token) => {
      if (!token) {
        push("/");
      }
    });
  }, []);

  const { push } = useRouter();
  const onLogout = async () => {
    logout();
    push("/");
  };

  const onDeleteAccount = async () => {};

  return (
    <>
      <header className="my-64">
        <img src="logo.svg" alt="logo" className="max-w-sm" />
      </header>
      <main>
        <div className="w-full flex gap-64">
          <div className="flex flex-col gap-64">
            <form
              onSubmit={changePasswordForm.handleSubmit(onSubmitChangePassword)}
              className="flex flex-col gap-32"
            >
              <h1>Change password</h1>
              <div className="flex flex-col gap-8">
                <TextField
                  {...changePasswordForm.register("email")}
                  placeholder="Current Email"
                />
                <Button
                  disabled={
                    changePasswordForm.formState.isSubmitSuccessful ||
                    changePasswordForm.formState.isSubmitting
                  }
                  type="submit"
                >
                  change password
                </Button>
                <ErrorSlot>
                  <p>{changePasswordError.email?.message}</p>
                </ErrorSlot>
                {changePasswordForm.formState.isSubmitSuccessful &&
                  !changePasswordForm.formState.isDirty && (
                    <ErrorSlot>
                      <p>Please check your inbox</p>
                    </ErrorSlot>
                  )}
              </div>
            </form>
            <form
              onSubmit={changeEmailForm.handleSubmit(onSubmitChangeEmail)}
              className="flex flex-col gap-32"
            >
              <h1>Change email</h1>
              <div className="flex flex-col gap-8">
                <TextField
                  {...changeEmailForm.register("email")}
                  placeholder="Current Email"
                />
                <Button
                  disabled={
                    changeEmailForm.formState.isSubmitSuccessful ||
                    changeEmailForm.formState.isSubmitting
                  }
                  type="submit"
                >
                  change email
                </Button>
                <ErrorSlot>
                  <p>{changeEmailError.email?.message}</p>
                </ErrorSlot>
                {changeEmailForm.formState.isSubmitSuccessful &&
                  !changeEmailForm.formState.isDirty && (
                    <ErrorSlot>
                      <p>Please check your inbox</p>
                    </ErrorSlot>
                  )}
              </div>
            </form>
          </div>
          <div className="flex flex-col gap-64">
            <div className="flex flex-col gap-32">
              <h1>Change core language</h1>
              <Button asChild>
                <Link href="core-language">
                  {baseLanguage ? baseLanguage : "Loading..."}
                </Link>
              </Button>
            </div>
            <div className="flex flex-col gap-32">
              <h1>Change account</h1>
              <div className=" flex flex-col gap-16">
                <Button onClick={onLogout}>logout</Button>
                <Button onClick={onDeleteAccount} intent="danger">
                  delete account
                </Button>
              </div>
            </div>
          </div>
        </div>
        <div className="w-full py-128 flex justify-center">
          <Button size="half" asChild>
            <Link href="dashboard">back</Link>
          </Button>
        </div>
      </main>
    </>
  );
};

export default Settings;
