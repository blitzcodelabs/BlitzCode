import { Slot } from "@radix-ui/react-slot";
import { cva, type VariantProps } from "class-variance-authority";
import { ButtonHTMLAttributes, ComponentPropsWithRef, forwardRef } from "react";

const styles = cva(
  "rounded py-8 w-512 text-background text-center uppercase transition-colors duration-300 hover:bg-secondary",
  {
    variants: {
      intent: {
        primary: "bg-primary",
        accent: "bg-accent ",
      },
    },
    defaultVariants: {
      intent: "primary",
    },
  }
);

export interface Props
  extends ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof styles> {
  asChild?: boolean;
}

const Button = forwardRef<HTMLButtonElement, Props>(
  ({ className, intent, asChild = false, ...props }, ref) => {
    const Comp = asChild ? Slot : "button";
    return (
      <Comp className={styles({ intent, className })} ref={ref} {...props} />
    );
  }
);
Button.displayName = "Button";
export default Button;
