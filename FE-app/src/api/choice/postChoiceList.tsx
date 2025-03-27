import { dev, domain, jsonHeaders } from "@/api/config";
import { ChoiceListType, testChoiceList } from "@/types/choice/choiceList";

export const postChoiceList = async (
  choiceList: postChoiceListType,
  themeId: number
): Promise<ChoiceListType> => {
  if (dev) {
    return new Promise<ChoiceListType>((resolve) => {
      resolve(testChoiceList);
    });
  }
  const response = await fetch(`${domain}/choiceList/${themeId}`, {
    method: "POST",
    headers: jsonHeaders,
    body: JSON.stringify({ choiceList: choiceList }),
  });
  if (!response.ok) {
    throw new Error(`status: ${response.status}`);
  }
  return await response.json();
};

export type postChoiceListType = [
  {
    choice: string;
  }
];
