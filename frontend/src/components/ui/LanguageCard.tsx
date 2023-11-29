import Image from "next/image";
import Button from "./Button";
import clsx from "clsx";
import { Item } from "@radix-ui/react-radio-group";
import { ComponentPropsWithoutRef, ElementRef, forwardRef } from "react";

interface Props {
  imageFile: string;
}

const LanguageCard = forwardRef<
  ElementRef<typeof Item>,
  ComponentPropsWithoutRef<typeof Item> & Props
>(({imageFile, value, ...props}, forwardedRef) => (
  <Item value={value} {...props} ref={forwardedRef} asChild>
    <Button
      className={clsx(
        "data-[state=checked]:bg-accent",
        "data-[state=checked]:hover:bg-accent",
        "p-0 w-256 h-256 shrink-0 hover:-translate-y-8"
      )}
    >
      <div className="flex flex-col justify-center items-center gap-8">
        <Image
          className="rounded"
          src={`/${imageFile}`}
          alt={`${value} icon`}
          width={128}
          height={128}
        ></Image>
        <h2>{value}</h2>
      </div>
    </Button>
  </Item>
));
LanguageCard.displayName = "LanguageCard";

export default LanguageCard;
