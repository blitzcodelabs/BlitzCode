"use client";

import Header from "./Header";
import { Content, Item, Root, Trigger } from "@radix-ui/react-accordion";
import Button from "../ui/Button";
import Link from "next/link";
import {useRouter} from "next/navigation";
import {useEffect, useState} from "react";
import {getWithAuth} from "@/lib/request";
import {Module} from "@/lib/types";

const Dashboard = () => {
    const [modules, setModules] = useState<Module[] | null>(null)
    const { push } = useRouter();
    useEffect( () => {
        getWithAuth("/modules").then(res => res?.json()).then(data => {
            if (!data) {
                push("/");
                return;
            }
            setModules(data);
        })
    }, []);
    if (!modules) {
        return <div>Loading...</div>
    }
  return (
    <>
      <Header></Header>
      <main className="mb-32">
        <Root type="single" className="flex flex-col gap-16" collapsible>
          {modules.map((module) => (
            <Button
              key={module.name}
              className="p-0 w-fit hover:bg-primary"
              asChild
            >
              <Item value={module.name}>
                <Trigger className="w-1024 p-32 text-2xl hover:cursor-pointer flex justify-between">
                  <p>{module.name}</p>
                  <p>{`${module.lessons.reduce(
                    (count, { sectionsCompleted, sectionsTotal }) =>
                      sectionsCompleted === sectionsTotal ? count + 1 : count,
                    0
                  )}/${module.lessons.length}`}</p>
                </Trigger>
                <Content className="data-[state=open]:animate-slideDown data-[state=closed]:animate-slideUp overflow-hidden">
                  <div className="mb-32 mx-32 flex flex-col gap-8">
                    {module.lessons.map((lesson) => (
                      <Button
                        key={lesson.name}
                        intent="secondary"
                        className="p-16 w-full text-lg flex justify-between font-mono normal-case"
                        asChild
                      >
                        <Link href="lesson">
                          <p>{lesson.name}</p>
                          <p>{`${100 * lesson.sectionsCompleted / lesson.sectionsTotal}%`}</p>
                        </Link>
                      </Button>
                    ))}
                  </div>
                </Content>
              </Item>
            </Button>
          ))}
        </Root>
      </main>
    </>
  );
};

export default Dashboard;
