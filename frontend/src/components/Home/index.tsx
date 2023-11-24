import React from "react";
import Button from "@/components/ui/Button";
import Link from "next/link";

const Home = () => {
  return (
    <main className="h-screen flex flex-col justify-center items-center gap-64">
      {/* Logo and tagline */}
      <div className="flex flex-col justify-center items-center gap-16">
        <img
          className="max-w-3xl transition hover:-translate-y-4 hover:drop-shadow-lg duration-300"
          src="logo.svg"
          alt="logo"
        />
        <h1 className="heading">Become fluent in a programming language.</h1>
      </div>

      {/* Buttons */}
      <div className="flex flex-col justify-center items-center gap-16">
        <Button intent={"accent"} asChild>
          <Link href="get-started">get started</Link>
        </Button>
        <Button className="text-sm" asChild>
          <Link href="login">i alrady have an account</Link>
        </Button>
      </div>
    </main>
  );
};

export default Home;
