import { Language } from "@/lib/types";
import LanguageCard from "./LanguageCard";
import { Root } from "@radix-ui/react-radio-group";
import { forwardRef, ElementRef, ComponentPropsWithoutRef } from "react";
import { useQuery } from "react-query";
import { get } from "@/lib/request";

const LanguageGroup = forwardRef<
  ElementRef<typeof Root>,
  ComponentPropsWithoutRef<typeof Root>
>((props, forwardedRef) => {
  const { data } = useQuery<Language[]>(
    ["languages"],
    async (): Promise<Language[]> => get("/languages")
  );

  return (
    <Root {...props} ref={forwardedRef} className="flex gap-32">
      {data?.map((lang) => (
        <LanguageCard
          key={lang.fullName}
          value={lang.fullName}
          imageFile={lang.id.toLowerCase() + ".png"}
        ></LanguageCard>
      ))}
    </Root>
  );
});
LanguageGroup.displayName = "LanguageGroup";

export default LanguageGroup;
