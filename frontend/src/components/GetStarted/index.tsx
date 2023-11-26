"use client";

import { useSearchParams, useRouter } from "next/navigation";
import CoursePicker from "./CoursePicker";
import Tutorial from "./Tutorial";

const steps = ["course-picker", "tutorial"] as const;
type Step = typeof steps[number];

const GetStarted = () => {
  const params = useSearchParams();
  const step: Step = (params.get("step") as Step) ?? "course-picker";

  return (
    <main>
      {step === "course-picker" && <CoursePicker></CoursePicker>}
      {step === "tutorial" && <Tutorial></Tutorial>}
    </main>
  );
};

export default GetStarted;
