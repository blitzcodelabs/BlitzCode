import { ComponentPropsWithoutRef } from "react";
import { twMerge } from "tailwind-merge";

const ErrorText = ({
  className,
  children,
  ...props
}: ComponentPropsWithoutRef<"span">) => (
  <span className={twMerge("font-mono text-fail", className)} {...props}>
    {children}
  </span>
);

export default ErrorText;
