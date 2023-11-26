"use client";

import Nav from "./Nav";
import { modules } from "@/placeholders/modules.json";
import { Module } from "@/lib/types";
import {
  Content,
  Header,
  Item,
  Root,
  Trigger,
} from "@radix-ui/react-accordion";
import Button from "../ui/Button";

const Dashboard = () => {
  // const res = modules.at(0)?.lessons.at(0)?.name;
  // console.log(res);
  const moduleList = modules as Module[];

  return (
    <>
      <Nav></Nav>
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
                    (count, { progress }) =>
                      progress === 100 ? count + 1 : count,
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
                      >
                        <p>{lesson.name}</p>
                        <p>{`${lesson.progress}%`}</p>
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
