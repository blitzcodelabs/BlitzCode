"use client";

import Button from "../ui/Button";
import LanguageGroup from "../ui/LanguageGroup";
import Link from "next/link";
import { Controller, SubmitHandler, useForm } from "react-hook-form";

interface Inputs {
  baseLanguage: string;
  languageToLearn: string;
}

const GetStarted = () => {
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
    <main>
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="h-screen flex flex-col justify-center items-center gap-64"
      >
        {/* I am most comfortable with... */}
        <div className="w-fit flex flex-col gap-32">
          <h1>I am most comfortable with...</h1>
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
          <h1>I want to learn...</h1>
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
          <Link href={"get-started/tutorial"} className="block">
            next
          </Link>
        </Button>
      </form>
    </main>
  );
};

export default GetStarted;
