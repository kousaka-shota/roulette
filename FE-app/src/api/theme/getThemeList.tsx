import { testThemeList, ThemeListType } from "@/types/theme/themeList";
import { dev, domain, jsonHeaders } from "@/api/config";

export const getThemeList = async (): Promise<GetThemeListType> => {
  if (dev) {
    return new Promise<GetThemeListType>((resolve) => {
      resolve({ results: testThemeList });
    });
  }
  const response = await fetch(`${domain}/theme/`, {
    method: "GET",
    headers: jsonHeaders,
  });
  if (!response.ok) {
    throw new Error(`status: ${response.status}`);
  }
  return await response.json();
};

type GetThemeListType = {
  results: ThemeListType;
};
