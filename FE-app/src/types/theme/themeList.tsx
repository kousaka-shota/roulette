export type ThemeType = {
  id: number;
  title: string;
};

export type ThemeListType = ThemeType[];

export const testThemeList: ThemeListType = [
  {
    id: 1,
    title: "theme1",
  },
  {
    id: 2,
    title: "theme2",
  },
  {
    id: 3,
    title: "theme3",
  },
];

export const initialTheme: ThemeType = {
  id: 0,
  title: "",
};
