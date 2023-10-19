import { Button } from "@/components/ui/button";

const Home = () => {
  return (
    <section className="flex h-screen items-center justify-center bg-primary">
      {/* FLOATING BOX */}
      <div className="flex w-1/2 flex-col items-center gap-12">
        {/* HEADING */}
        <div className="text-center">
          {/* LOGO */}
          <h1 className="font-mono text-6xl tracking-tight md:text-8xl">
            BlitzCode
          </h1>
          {/* DESCRIPTION */}
          <p className="w-full text-lg md:text-2xl">
            Become fluent in a programming language.
          </p>
        </div>
        {/* BUTTONS */}
        <div className="flex w-1/2 flex-col gap-2">
          <Button variant={"outline"}>GET STARTED</Button>
          <Button variant={"ghost"}>I HAVE AN ACCOUNT</Button>
        </div>
      </div>
    </section>
  );
};

export default Home;
