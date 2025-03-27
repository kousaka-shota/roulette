import { getChoiceList } from "@/api/choice/getChoiceList";
import { ChoiceListType } from "@/types/choice/choiceList";
import { ThemeListType, ThemeType } from "@/types/theme/themeList";

export const convertSelectOptions = (themeList: ThemeListType) => {
  const themeOptions = themeList.map(({ id, title }) => {
    return { value: id, label: title };
  });
  return themeOptions;
};

export const searchTheme = (
  id: number,
  themeList: ThemeListType
): ThemeType => {
  const theme = themeList.find((theme) => theme.id === id);
  if (!theme) {
    throw new Error(`テーマが見つかりません: id=${id}`);
  }
  return theme;
};
export const searchChoiceList = async (id: number) => {
  const { results } = await getChoiceList(id);
  return results;
};

export const generateEditedChoiceList = (
  themeId: number,
  choiceList: ChoiceListType
): ChoiceListType => {
  return choiceList.map((choice) => {
    if (!choice.hasOwnProperty("id")) {
      choice.id = 0;
      choice.themeId = themeId;
    }
    return choice;
  });
};
