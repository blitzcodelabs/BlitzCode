"use client";

import Button from "../ui/Button";
import LanguageGroup from "../ui/LanguageGroup";
import Link from "next/link";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { postWithAuth } from "@/lib/request";
import { useRouter } from "next/navigation";

interface Inputs {
  baseLanguage: string;
  languageToLearn: string;
}

const CoursePicker = () => {
  const {
    handleSubmit,
    control,
    getValues,
    formState: { isDirty, isValid },
  } = useForm<Inputs>({
    mode: "onChange",
    defaultValues: { baseLanguage: "", languageToLearn: "" },
  });

  const router = useRouter();
  const onSubmit: SubmitHandler<Inputs> = (data) => {
    postWithAuth("/account/baseLanguage", data.baseLanguage);
    postWithAuth("/account/targetLanguage", data.languageToLearn);
    console.log(data);
    router.push("get-started/?step=tutorial");
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
            validate: (v) => v !== getValues("languageToLearn"),
          }}
          render={({ field }) => (
            <LanguageGroup onValueChange={field.onChange}></LanguageGroup>
          )}
        ></Controller>
      </div>

      {/* I want to learn... */}
      <div className="w-fit flex flex-col gap-32">
        <label htmlFor="I want to learn...">I want to learn...</label>
        <Controller
          control={control}
          name="languageToLearn"
          rules={{
            required: true,
            validate: (v) => v !== getValues("baseLanguage"),
          }}
          render={({ field }) => (
            <LanguageGroup onValueChange={field.onChange}></LanguageGroup>
          )}
        ></Controller>
      </div>

      <Button type="submit" disabled={!isDirty || !isValid} size="half">
        next
      </Button>
    </form>
  );
};

export default CoursePicker;
