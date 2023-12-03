import { CircleNotch } from "@phosphor-icons/react";

const LoadingScreen = () => {
  return (
    <main className="h-screen flex justify-center items-center">
      <CircleNotch
        size="128px"
        weight="bold"
        className="text-primary animate-spin"
      />
    </main>
  );
};

export default LoadingScreen;
