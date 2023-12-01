"use client";

import { useForm } from "react-hook-form";
import { z } from "zod";
import ErrorSlot from "../ui/ErrorSlot";
import TextField from "../ui/TextField";
import Button from "../ui/Button";
import { zodResolver } from "@hookform/resolvers/zod";
import { logout } from "@/lib/auth";
import Link from "next/link";
import { useRouter } from "next/navigation";

const changePasswordSchema = z
  .object({
    oldPassword: z.string().min(8),
    newPassword: z.string().min(8),
  })
  .refine((data) => data.oldPassword === data.newPassword, {
    message: "Passwords must match",
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
  const onSubmitChangePassword = async () => {
    await new Promise((resolve) => setTimeout(resolve, 1000));
    changePasswordForm.reset();
  };

  const changeEmailForm = useForm<ChangeEmailSchema>({
    resolver: zodResolver(changeEmailSchema),
  });
  const changeEmailError = changeEmailForm.formState.errors;
  const onSubmitChangeEmail = async () => {
    await new Promise((resolve) => setTimeout(resolve, 1000));
    changeEmailForm.reset();
  };

  const { push } = useRouter();
  const onLogout = async () => {
    logout();
    push("/")
  };

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
                  {...changePasswordForm.register("oldPassword")}
                  type="password"
                  placeholder="Password"
                />
                <TextField
                  {...changePasswordForm.register("newPassword")}
                  type="password"
                  placeholder="Confirm Password"
                />
                {Object.values(changePasswordError).length !== 0 && (
                  <ErrorSlot>
                    <p>Passwords do not match</p>
                  </ErrorSlot>
                )}
                {changePasswordForm.formState.isSubmitSuccessful &&
                  !changePasswordForm.formState.isDirty && (
                    <ErrorSlot>
                      <p>Your password has been changed</p>
                    </ErrorSlot>
                  )}
              </div>
              <Button
                disabled={changePasswordForm.formState.isSubmitting}
                type="submit"
              >
                save
              </Button>
            </form>
            <form
              onSubmit={changeEmailForm.handleSubmit(onSubmitChangeEmail)}
              className="flex flex-col gap-32"
            >
              <h1>Change email</h1>
              <div className="flex flex-col gap-8">
                <TextField
                  {...changeEmailForm.register("email")}
                  placeholder="Email"
                />
                {changeEmailError.email && (
                  <ErrorSlot>
                    <p>{changeEmailError.email.message}</p>
                  </ErrorSlot>
                )}
                {changeEmailForm.formState.isSubmitSuccessful &&
                  !changeEmailForm.formState.isDirty && (
                    <ErrorSlot>
                      <p>Your email has been changed</p>
                    </ErrorSlot>
                  )}
              </div>
              <Button
                disabled={changeEmailForm.formState.isSubmitting}
                type="submit">
                save
              </Button>
            </form>
          </div>
          <div>
            <div className="flex flex-col gap-32">
              <h1>Change core language</h1>
              <Button asChild>
                <Link href="core-language">java</Link>
              </Button>
            </div>
            <Button className="my-128" onClick={() => {onLogout()}}>
              Logout
            </Button>
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
