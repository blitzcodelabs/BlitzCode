"use client";

import { type SubmitHandler, useForm } from "react-hook-form";
import { z } from "zod";
import ErrorSlot from "../ui/ErrorSlot";
import TextField from "../ui/TextField";
import Button from "../ui/Button";
import { zodResolver } from "@hookform/resolvers/zod";
import { getidToken, logout } from "@/lib/auth";
import Link from "next/link";
import {useRouter, useSearchParams} from "next/navigation";
import { useEffect, useState } from "react";
import { deleteWithAuth, getWithAuth, post, postWithAuth } from "@/lib/request";
import {
  Close,
  Content,
  Description,
  Overlay,
  Portal,
  Root,
  Trigger,
} from "@radix-ui/react-dialog";
import {Language} from "@/lib/types";

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
  const params = useSearchParams();
  const [baseLanguage, setBaseLanguage] = useState(params.get("baseLanguage") || "Loading...");
  useEffect(() => {
    getWithAuth("/account/baseLanguage")
      .then((res) => res?.json())
      .then((data: Language) => {
        if (data) {
          setBaseLanguage(data.fullName);
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

  const onDeleteAccount = async () => await deleteWithAuth("/account");

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
                  placeholder="Email"
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
                  placeholder="New Email"
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
                      <p>Your email has been updated</p>
                    </ErrorSlot>
                  )}
              </div>
            </form>
          </div>
          <div className="flex flex-col gap-64">
            <div className="flex flex-col gap-32">
              <h1>Change core language</h1>
              <Button asChild>
                <Link href="base-language">
                  {baseLanguage}
                </Link>
              </Button>
            </div>
            <div className="flex flex-col gap-32">
              <h1>Change account</h1>
              <div className=" flex flex-col gap-16">
                <Button onClick={onLogout}>logout</Button>
                <Root>
                  <Trigger asChild>
                    <Button intent="danger">delete account</Button>
                  </Trigger>
                  <Portal>
                    <Overlay className="fixed inset-0 bg-background"></Overlay>
                    <Content className="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2">
                      <div className="flex flex-col gap-64">
                        <Description asChild>
                          <h1>Are you sure you want to delete your account?</h1>
                        </Description>
                        <div className="flex flex-col items-center gap-16">
                          <Button intent="danger" onClick={onDeleteAccount}>
                            yes, delete my account
                          </Button>
                          <Close asChild>
                            <Button className="hov">nevermind</Button>
                          </Close>
                        </div>
                      </div>
                    </Content>
                  </Portal>
                </Root>
              </div>
            </div>
          </div>
        </div>
        <div className="py-128 flex justify-center">
          <Button size="half" asChild>
            <Link href="dashboard">back</Link>
          </Button>
        </div>
      </main>
    </>
  );
};

export default Settings;
