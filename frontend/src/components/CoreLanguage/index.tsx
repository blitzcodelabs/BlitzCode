"use client";

import Button from "../ui/Button";
import LanguageGroup from "../ui/LanguageGroup";
import Link from "next/link";
import { Controller, SubmitHandler, useForm } from "react-hook-form";

interface Inputs {
  coreLanguage: string;
}

const CoreLanguage = () => {
  const {
    handleSubmit,
    control,
    formState: { isDirty },
  } = useForm<Inputs>({
    mode: "onChange",
    defaultValues: { coreLanguage: "" },
  });

  const onSubmit: SubmitHandler<Inputs> = (data) => {
    console.log(data);
  };

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="h-screen flex flex-col justify-center items-center gap-64"
    >
      {/* I am most comfortable with... */}
      <div className="w-fit flex flex-col gap-32">
        <label htmlFor="I am most comfortable with...">
          I am most comfortable with...
        </label>
        <Controller
          control={control}
          name="coreLanguage"
          rules={{
            required: true,
          }}
          render={({ field }) => (
            <LanguageGroup onValueChange={field.onChange}></LanguageGroup>
          )}
        ></Controller>
      </div>

      <Button type="submit" disabled={!isDirty} size="half">
        <Link href="settings" className="block">
          save
        </Link>
      </Button>
    </form>
  );
};

export default CoreLanguage;