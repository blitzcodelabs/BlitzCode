import { languages } from "@/placeholders/languages.json";
import LanguageCard from "./LanguageCard";
import { Root } from "@radix-ui/react-radio-group";
import { forwardRef, ElementRef, ComponentPropsWithoutRef } from "react";

const LanguageGroup = forwardRef<
  ElementRef<typeof Root>,
  ComponentPropsWithoutRef<typeof Root>
>((props, forwardedRef) => (
  <Root {...props} ref={forwardedRef} className="flex gap-32">
    {languages.map((lang) => (
      <LanguageCard key={lang} value={lang}></LanguageCard>
    ))}
  </Root>
));
LanguageGroup.displayName = "LanguageGroup";

export default LanguageGroup;
