"use client";

import Button from "../ui/Button";
import LanguageGroup from "../ui/LanguageGroup";
import Link from "next/link";
import { Controller, SubmitHandler, useForm } from "react-hook-form";

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

  const onSubmit: SubmitHandler<Inputs> = (data) => console.log(data);

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
        <Link
          href={{ pathname: "get-started", query: { step: "tutorial" } }}
          className="block"
        >
          next
        </Link>
      </Button>
    </form>
  );
};

export default CoursePicker;
