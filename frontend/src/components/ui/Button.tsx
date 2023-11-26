import { Slot } from "@radix-ui/react-slot";
import { cva, type VariantProps } from "class-variance-authority";
import { ButtonHTMLAttributes, ComponentPropsWithoutRef, forwardRef } from "react";
import { twMerge } from "tailwind-merge";

const styles = cva(
  "rounded py-8 text-background text-center text-xl uppercase transition duration-300 hover:bg-secondary disabled:pointer-events-none disabled:opacity-50",
  {
    variants: {
      intent: {
        primary: "bg-primary",
        accent: "bg-accent ",
        secondary: "bg-secondary hover:bg-background hover:text-secondary"
      },
      size: {
        full: "w-512",
        half: "w-256",
        quarter: "w-128 text-base"
      },
    },
    defaultVariants: {
      intent: "primary",
      size: "full",
    },
  }
);

interface Props
  extends ComponentPropsWithoutRef<"button">,
    VariantProps<typeof styles> {
  asChild?: boolean;
}

const Button = forwardRef<HTMLButtonElement, Props>(
  ({ className, intent, size, asChild = false, ...props }, ref) => {
    const Comp = asChild ? Slot : "button";
    return (
      <Comp
        className={twMerge(styles({ intent, size, className }))}
        ref={ref}
        {...props}
      />
    );
  }
);
Button.displayName = "Button";
export default Button;
