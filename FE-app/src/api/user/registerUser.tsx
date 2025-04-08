import { dev, domain, jsonHeaders } from "../config";

export const registerUser = async ({
  username,
  password,
}: {
  username: string;
  password: string;
}) => {
  if (dev) {
    return;
  }
  const response = await fetch(`${domain}/api/auth/register`, {
    method: "POST",
    headers: jsonHeaders,
    body: JSON.stringify({ name: username, password: password }),
  });
  if (!response.ok) {
    throw new Error(`status: ${response.status}`);
  }
  return await response;
};
