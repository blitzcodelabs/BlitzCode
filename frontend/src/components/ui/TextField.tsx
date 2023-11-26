import {
  ComponentPropsWithRef,
  ComponentPropsWithoutRef,
  InputHTMLAttributes,
  forwardRef,
} from "react";

const TextField = forwardRef<
  HTMLInputElement,
  ComponentPropsWithoutRef<"input">
>(({ ...props }, ref) => (
  <input
    {...props}
    ref={ref}
    className="rounded border border-secondary p-8 font-mono text-lg text-secondary placeholder:text-secondary placeholder:text-opacity-50 w-512"
  />
));
TextField.displayName = "TextField";

export default TextField;
