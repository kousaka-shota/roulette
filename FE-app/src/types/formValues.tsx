import { postChoiceListType } from "@/api/choice/postChoiceList";
import { ChoiceListType } from "@/types/choice/choiceList";

export interface EditFormValues {
  themeId: number;
  title: string;
  choiceList: ChoiceListType;
}

// typeでなくinterfaceを使うのはここからOverrideできるかららしい
export interface NewFormValues {
  title: string;
  choiceList: postChoiceListType;
}
