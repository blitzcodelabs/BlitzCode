import { Language } from "@/lib/types";
import LanguageCard from "./LanguageCard";
import { Root } from "@radix-ui/react-radio-group";
import { forwardRef, ElementRef, ComponentPropsWithoutRef } from "react";
import { useQuery } from "react-query";

const LanguageGroup = forwardRef<
  ElementRef<typeof Root>,
  ComponentPropsWithoutRef<typeof Root>
>((props, forwardedRef) => {
  const { data } = useQuery<Language[]>(["languages"], async () => {
    const res = await fetch("http://localhost:8080/languages");
    const data  = await res.json();
    return data;
  });

  return (
    <Root {...props} ref={forwardedRef} className="flex gap-32">
      {data?.map((lang) => (
        <LanguageCard key={lang.name} value={lang.name} imageFile={lang.imageFile} ></LanguageCard>
      ))}
    </Root>
  );
});
LanguageGroup.displayName = "LanguageGroup";

export default LanguageGroup;
