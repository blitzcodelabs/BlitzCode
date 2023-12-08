"use client";

import Button from "../ui/Button";
import LanguageGroup from "../ui/LanguageGroup";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { postWithAuth } from "@/lib/request";
import { useRouter } from "next/navigation";

interface Inputs {
  baseLanguage: string;
}

const BaseLanguage = () => {
  const {
    handleSubmit,
    control,
    formState: { isDirty },
  } = useForm<Inputs>({
    mode: "onChange",
    defaultValues: { baseLanguage: "" },
  });

  const router = useRouter();
  const onSubmit: SubmitHandler<Inputs> = (data) => {
    // TODO is it possible to hide this "baseLanguage" parameter
    postWithAuth("/account/baseLanguage", JSON.stringify({id: data.baseLanguage.toUpperCase()} ));
    console.log(data);
    router.push(`settings?baseLanguage=${data.baseLanguage}`);
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="h-screen flex flex-col justify-center items-center gap-64"
    >
      <div className="w-fit flex flex-col gap-32">
        <label htmlFor="I am most comfortable with...">
          I am most comfortable with...
        </label>
        <Controller
          control={control}
          name="baseLanguage"
          rules={{
            required: true,
          }}
          render={({ field }) => (
            <LanguageGroup onValueChange={field.onChange}></LanguageGroup>
          )}
        ></Controller>
      </div>

      <Button type="submit" disabled={!isDirty} size="half">
        save
      </Button>
    </form>
  );
};

export default BaseLanguage;
