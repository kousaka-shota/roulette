import { dev, domain, jsonHeaders } from "@/api/config";
import { ChoiceListType, testChoiceList } from "@/types/choice/choiceList";

export const getChoiceList = async (
  themeId: number
): Promise<GetChoiceListType> => {
  if (dev) {
    return new Promise<GetChoiceListType>((resolve) => {
      resolve({ results: testChoiceList });
    });
  }
  const response = await fetch(`${domain}/choiceList/${themeId}`, {
    method: "GET",
    headers: jsonHeaders,
  });
  if (!response.ok) {
    throw new Error(`status: ${response.status}`);
  }
  return await response.json();
};

type GetChoiceListType = {
  results: ChoiceListType;
};
