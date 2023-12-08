"use client";

import Button from "../ui/Button";
import { type SubmitHandler, useForm } from "react-hook-form";
import clsx from "clsx";
import { useEffect, useState } from "react";
import * as Progress from "@radix-ui/react-progress";
import Link from "next/link";
import { getWithAuth, postWithAuth } from "@/lib/request";
import { Language, Question } from "@/lib/types";
import { useRouter } from "next/navigation";
import { X } from "@phosphor-icons/react";
import LoadingScreen from "@/components/ui/LoadingScreen";
import { useQuery } from "react-query";

interface Inputs {
  selectedIndex: number;
}

const Lesson = ({ params }: { params: { id: string } }) => {
  const {
    handleSubmit,
    getValues,
    setValue,
    reset,
    formState: { isSubmitted },
  } = useForm<Inputs>();

  const [questionIndex, setQuestionIndex] = useState(0);
  const [questions, setQuestions] = useState<Question[] | null>(null);
  const [lessonCompletion, setLessonCompletion] = useState<number>();

  const { push } = useRouter();
  useEffect(() => {
    getWithAuth("/questions", params.id)
      .then((res) => res?.json())
      .then((data) => {
        if (!data) {
          push("/");
          return;
        }
        setQuestions(data);
      });
  }, [params.id, push]);

  const abbreviationQuery = useQuery("language", async () => {
    const res = await getWithAuth("/account/targetLanguage");
    const data = (await res?.json()) as Language;
    return data.shortName;
  });

  if (!questions) {
    return <LoadingScreen />;
  }
  const progress = Math.floor((questionIndex / questions.length) * 100);

  const onSubmit: SubmitHandler<Inputs> = () => {
    if (questionIndex + 1 === questions.length) {
      postWithAuth("/questions/completed", JSON.stringify(questions), params.id)
        .then((res) => res?.json())
        .then(({ sectionsCompleted, sectionsTotal }) => {
          setLessonCompletion((100 * sectionsCompleted) / sectionsTotal);
        });
    }
    const timer = setTimeout(() => {
      setQuestionIndex(questionIndex + 1);
      reset();
    }, 1500);
    return () => clearTimeout(timer);
  };

  return (
    <>
      <nav className="w-full p-128 flex items-center">
        <Link href="/dashboard" className="absolute -translate-x-64">
          <X size="32px" className="text-secondary hover:text-primary" />
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
                      (questions[questionIndex].answerIndex === index
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
                    <code className="text-lg normal-case text-left">
                      {choice}
                    </code>
                  </div>
                  <code className="text-lg">{abbreviationQuery.data}</code>
                </Button>
              ))}
            </form>
          </div>
        ) : lessonCompletion ? (
          <div className="h-full flex flex-col justify-center items-center gap-32">
            <h1 className="text-center leading-loose">
              Nice Work!
              <br />
              <span className="font-sans text-9xl">{lessonCompletion}%</span>
              <br />
              of lesson completed.
            </h1>

            <Button size="half" onClick={() => setQuestionIndex(0)}>
              keep going!
            </Button>

            <Button size="half" className="m-128" asChild>
              <Link href="/dashboard">back</Link>
            </Button>
          </div>
        ) : (
          <LoadingScreen />
        )}
      </main>
    </>
  );
};

export default Lesson;
