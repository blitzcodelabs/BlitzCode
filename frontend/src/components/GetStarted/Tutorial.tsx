import React from "react";
import Button from "../ui/Button";
import Link from "next/link";
import {useSearchParams} from "next/navigation";

const Tutorial = () => {
    const params = useSearchParams();
    const baseLanguage = params.get("baseLanguage");
    const targetLanguage = params.get("targetLanguage");
  return (
    <div className="h-screen flex flex-col justify-center items-center gap-64">
      <img src="tutorial.svg" alt="tutorial" />
      <Button size="half" asChild>
        <Link href={`sign-up?baseLanguage=${baseLanguage}&targetLanguage=${targetLanguage}`}>sign up</Link>
      </Button>
    </div>
  );
};

export default Tutorial;
