import { dev, domain, jsonHeaders } from "@/api/config";

export const deleteTheme = async (id: number) => {
  if (dev) {
    return;
  }
  const response = await fetch(`${domain}/theme/${id}`, {
    method: "DELETE",
    headers: jsonHeaders,
  });
  if (!response.ok) {
    throw new Error(`status: ${response.status}`);
  }
  return await response.json();
};
