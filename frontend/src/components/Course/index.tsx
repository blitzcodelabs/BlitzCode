"use client";

import Button from "../ui/Button";
import LanguageGroup from "../ui/LanguageGroup";
import Link from "next/link";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import { getWithAuth, postWithAuth } from "@/lib/request";
import { useRouter } from "next/navigation";
import { useQuery } from "react-query";

interface Inputs {
  targetLanguage: string;
}

const Course = () => {
  const {
    handleSubmit,
    control,
    formState: { isDirty, isValid },
  } = useForm<Inputs>({
    mode: "onChange",
    defaultValues: { targetLanguage: "" },
  });

  const router = useRouter();
  const onSubmit: SubmitHandler<Inputs> = (data) => {
    postWithAuth("/account/targetLanguage", data.targetLanguage);
    console.log(data);
    router.push("dashboard");
  };

  const baseLanguageQuery = useQuery("baseLanguage", async () => {
    const res = await getWithAuth("/account/baseLanguage");
    const data = res?.text();
    console.log(data);

    return data;
  });

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="h-screen flex flex-col justify-center items-center gap-64"
    >
      <div className="w-fit flex flex-col gap-32">
        <label htmlFor="I want to learn...">I want to learn...</label>
        <Controller
          control={control}
          name="targetLanguage"
          rules={{
            required: true,
            validate: (v) => v.toUpperCase() !== baseLanguageQuery.data,
          }}
          render={({ field }) => (
            <LanguageGroup onValueChange={field.onChange}></LanguageGroup>
          )}
        ></Controller>
      </div>

      <Button
        type="submit"
        disabled={!isDirty || !isValid || baseLanguageQuery.isFetching}
        size="half"
      >
        save
      </Button>
    </form>
  );
};

export default Course;
