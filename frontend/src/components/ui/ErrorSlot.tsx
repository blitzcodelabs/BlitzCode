import { Slot } from "@radix-ui/react-slot";
import { ComponentPropsWithoutRef, ElementRef, forwardRef } from "react";
import { twMerge } from "tailwind-merge";

const ErrorSlot = forwardRef<
  ElementRef<typeof Slot>,
  ComponentPropsWithoutRef<typeof Slot>
>(({ className, ...props }, ref) => (
  <Slot
    {...props}
    ref={ref}
    className={twMerge("font-mono text-secondary", className)}
  />
));
ErrorSlot.displayName = "ErrorSlot";

export default ErrorSlot;
