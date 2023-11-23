import { VariantProps, cva } from "class-variance-authority";
import Link from "next/link";
import React, { ComponentPropsWithRef } from "react";

type ButtonOrLinkProps = ComponentPropsWithRef<"button"> &
  ComponentPropsWithRef<"a">;

interface Props extends ButtonOrLinkProps, VariantProps<typeof styles> {}

const ButtonOrLink = ({ href, ...props }: Props) => {
  return href !== undefined ? (
    <Link href={href} {...props}></Link>
  ) : (
    <button {...props}></button>
  );
};

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

const Button = ({ intent, ...props }: Props) => {
  return <ButtonOrLink className={styles({ intent })} {...props} />;
};

export default Button;
