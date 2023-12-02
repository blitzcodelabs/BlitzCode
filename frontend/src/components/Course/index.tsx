"use client";

import Button from "../ui/Button";
import LanguageGroup from "../ui/LanguageGroup";
import Link from "next/link";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import {postWithAuth} from "@/lib/request";

interface Inputs {
  languageToLearn: string;
}

const Course = () => {
  const {
    handleSubmit,
    control,
    formState: { isDirty },
  } = useForm<Inputs>({
    mode: "onChange",
    defaultValues: { languageToLearn: "" },
  });

  const onSubmit: SubmitHandler<Inputs> = (data) => {
    postWithAuth("/account/targetLanguage", data.languageToLearn);
    console.log(data);
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="h-screen flex flex-col justify-center items-center gap-64"
    >
      {/* I want to learn... */}
      <div className="w-fit flex flex-col gap-32">
        <label htmlFor="I want to learn...">I want to learn...</label>
        <Controller
          control={control}
          name="languageToLearn"
          rules={{
            required: true,
          }}
          render={({ field }) => (
            <LanguageGroup onValueChange={field.onChange}></LanguageGroup>
          )}
        ></Controller>
      </div>

      <Button type="submit" disabled={!isDirty} size="half">
        <Link href="dashboard" className="block">
          next
        </Link>
      </Button>
    </form>
  );
};

export default Course;
