type ChoiceType = {
  id: number;
  choice: string;
  themeId: number;
};

export type ChoiceListType = ChoiceType[];

export const testChoiceList: ChoiceListType = [
  {
    id: 1,
    choice: "choice1",
    themeId: 1,
  },
  {
    id: 2,
    choice: "choice2",
    themeId: 1,
  },
  {
    id: 3,
    choice: "choice3",
    themeId: 1,
  },
  {
    id: 4,
    choice: "choice4",
    themeId: 1,
  },
  {
    id: 5,
    choice: "choice5",
    themeId: 1,
  },
];

export const initialChoiceList: ChoiceListType = [
  {
    id: 0,
    choice: "",
    themeId: 0,
  },
];
