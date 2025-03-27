import { dev, domain, jsonHeaders } from "@/api/config";
import { ChoiceListType, testChoiceList } from "@/types/choice/choiceList";

export const putChoiceList = async (
  themeId: number,
  choiceList: ChoiceListType
): Promise<putChoiceListType> => {
  if (dev) {
    return new Promise<putChoiceListType>((resolve) => {
      resolve({ results: testChoiceList });
    });
  }
  const response = await fetch(`${domain}/choiceList/${themeId}`, {
    method: "PUT",
    headers: jsonHeaders,
    body: JSON.stringify({ results: choiceList }),
  });
  if (!response.ok) {
    throw new Error(`status: ${response.status}`);
  }
  return await response.json();
};

type putChoiceListType = {
  results: ChoiceListType;
};
