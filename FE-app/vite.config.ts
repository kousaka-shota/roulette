import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  root: "./",
  resolve: {
    // ←追加
    alias: {
      // ←追加
      "@": "/src", // ←追加
    },
  },
});
