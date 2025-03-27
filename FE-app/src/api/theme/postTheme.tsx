import { ThemeType } from "@/types/theme/themeList";
import { dev, domain, jsonHeaders } from "@/api/config";

export const postTheme = async (title: string): Promise<ThemeType> => {
  if (dev) {
    return new Promise<ThemeType>((resolve) => {
      resolve({ id: 4, title: title });
    });
  }
  const response = await fetch(`${domain}/theme/`, {
    method: "POST",
    headers: jsonHeaders,
    body: JSON.stringify({ title: title }),
  });
  if (!response.ok) {
    throw new Error(`status: ${response.status}`);
  }
  return await response.json();
};
