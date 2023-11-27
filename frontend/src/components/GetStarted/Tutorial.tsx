import React from "react";
import Button from "../ui/Button";
import Link from "next/link";

const Tutorial = () => {
  return (
    <div className="h-screen flex flex-col justify-center items-center gap-64">
      <img src="tutorial.svg" alt="tutorial" />
      <Button size="half" asChild>
        <Link href={"sign-up"}>sign up</Link>
      </Button>
    </div>
  );
};

export default Tutorial;
