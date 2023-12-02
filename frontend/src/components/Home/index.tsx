"use client"

import React, {useEffect} from "react";
import Button from "@/components/ui/Button";
import Link from "next/link";
import {getidToken} from "@/lib/auth";
import {useRouter} from "next/navigation";

const Home = () => {
    const { push } = useRouter();
    useEffect(() => {
        getidToken().then(token => {
            if (token) {
                push("/dashboard");
            }
        })
    }, []);
  return (
    <main className="h-screen flex flex-col justify-center items-center gap-64">
      {/* Logo and tagline */}
      <div className="flex flex-col justify-center items-center gap-16">
        <img
          className="max-w-3xl transition hover:-translate-y-4 hover:drop-shadow-lg duration-300"
          src="logo.svg"
          alt="logo"
        />
        <h1 className="text-center text-2xl">
          Become fluent in a new programming language.
        </h1>
      </div>

      {/* Buttons */}
      <div className="flex flex-col justify-center items-center gap-16">
        <Button intent={"accent"} asChild>
          <Link href="get-started">get started</Link>
        </Button>
        <Button className="text-base" asChild>
          <Link href="login">i alrady have an account</Link>
        </Button>
      </div>
    </main>
  );
};

export default Home;
