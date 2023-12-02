"use client";

import Button from "../ui/Button";
import { type SubmitHandler, useForm } from "react-hook-form";
import clsx from "clsx";
import { useEffect, useState } from "react";
import * as Progress from "@radix-ui/react-progress";
import Link from "next/link";
import { Cross1Icon } from "@radix-ui/react-icons";
import {getWithAuth} from "@/lib/request";
import {Question} from "@/lib/types";
import {useRouter} from "next/navigation";

interface Inputs {
  selectedIndex: number;
}

const Lesson = () => {
  const {
    handleSubmit,
    getValues,
    setValue,
    reset,
    formState: { isSubmitted },
  } = useForm<Inputs>();

  const [questionIndex, setQuestionIndex] = useState(0);
  const [questions, setQuestions] = useState<Question[] | null>(null)
  const { push } = useRouter();
  useEffect( () => {
    getWithAuth("/questions").then(res => res?.json()).then(data => {
      if (!data) {
        push("/");
        return;
      }
      setQuestions(data.questions);
    })
  }, []);
  if (!questions) {
    return <div>Loading...</div>
  }
  const progress = Math.floor((questionIndex / questions.length) * 100);

  const onSubmit: SubmitHandler<Inputs> = () => {
    const timer = setTimeout(() => {
      setQuestionIndex(questionIndex + 1);
      reset();
    }, 1500);
    return () => clearTimeout(timer);
  };

  return (
    <>
      <nav className="w-full p-128 flex items-center">
        <Link href="dashboard" className="absolute -translate-x-64">
          <Cross1Icon className="w-32 h-32 hover:stroke-secondary text-secondary"></Cross1Icon>
        </Link>
        <Progress.Root
          value={progress}
          className="w-full h-16 rounded-full overflow-hidden bg-secondary"
        >
          <Progress.Indicator
            className="bg-primary w-full h-full transition-transform duration-500 ease-[cubic-bezier(0.65, 0, 0.35, 1)]"
            style={{
              transform: `translateX(-${100 - progress}%)`,
            }}
          ></Progress.Indicator>
        </Progress.Root>
      </nav>
      <main className="flex flex-col items-center">
        {questionIndex < questions.length ? (
          <div className="flex flex-col justify-center items-center gap-64">
            <div className="flex flex-col gap-32">
              <h1>What is the equivalent of...</h1>
              <div className="rounded border p-32 text-primary text-lg">
                <code>{questions[questionIndex].prompt}</code>
              </div>
            </div>
            <form
              onSubmit={handleSubmit(onSubmit)}
              className="flex flex-col gap-16"
            >
              {questions[questionIndex].choices.map((choice, index) => (
                <Button
                  key={index}
                  onClick={() => setValue("selectedIndex", index)}
                  type="submit"
                  disabled={isSubmitted}
                  className={clsx(
                    isSubmitted &&
                      (choice.isCorrect
                        ? "bg-success"
                        : index === getValues().selectedIndex && "bg-fail"),
                    isSubmitted &&
                      index === getValues().selectedIndex &&
                      "disabled:opacity-100",
                    "p-16 flex justify-between"
                  )}
                >
                  <div className="flex gap-16">
                    <p>{index + 1}</p>
                    <code className="text-lg normal-case">{choice.text}</code>
                  </div>
                  <code className="text-lg">js</code>
                </Button>
              ))}
            </form>
          </div>
        ) : (
          <div className="h-full flex flex-col justify-center items-center gap-32">
            <h1 className="text-center leading-loose">
              Nice Work!
              <br />
              <span className="font-sans text-9xl">50%</span>
              <br />
              of lesson completed.
            </h1>

            <Button size="half" onClick={() => setQuestionIndex(0)}>
              keep going!
            </Button>

            <Button size="half" className="m-128" asChild>
              <Link href="dashboard">back</Link>
            </Button>
          </div>
        )}
      </main>
    </>
  );
};

export default Lesson;
