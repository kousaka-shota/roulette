import { useState, useEffect } from "react";
import { Form, Select } from "antd";
import { ThemeForm } from "@/components/ThemeForm";
import { ChoiceListForm } from "@/components/ChoiceListForm";
import {
  initialTheme,
  ThemeListType,
  ThemeType,
} from "@/types/theme/themeList";
import { ChoiceListType, initialChoiceList } from "@/types/choice/choiceList";
import { putChoiceList } from "@/api/choice/putChoiceList";
import { FormButtons } from "@/components/FormButtons";
import {
  convertSelectOptions,
  generateEditedChoiceList,
  searchChoiceList,
  searchTheme,
} from "@/utils/formUtils";
import { EditFormValues } from "@/types/formValues";

export const ThChEditForm = ({
  closeThChModal,
  setIsRefresh,
  themeList,
}: {
  closeThChModal: () => void;
  setIsRefresh: () => void;
  themeList: ThemeListType;
}) => {
  const [form] = Form.useForm<EditFormValues>();

  const [editState, setEditState] = useState<{
    theme: ThemeType;
    choiceList: ChoiceListType;
  }>({
    theme: initialTheme,
    choiceList: initialChoiceList,
  });

  const updateThCh = async ({
    themeId,
    title,
    choiceList,
  }: {
    themeId: number;
    title: string;
    choiceList: ChoiceListType;
  }) => {
    const updatedChoiceList: ChoiceListType = generateEditedChoiceList(
      themeId,
      choiceList
    );
    const updatedTheme: ThemeType = { id: themeId, title: title };
    await putChoiceList(updatedTheme.id, updatedChoiceList);
    // FIX ME themeの文字列を更新する
    closeThChModal();
    setEditState({
      theme: initialTheme,
      choiceList: initialChoiceList,
    });
    setIsRefresh();
  };
  useEffect(() => {
    // フォームの値を更新
    form.setFieldsValue({
      themeId: editState.theme.id,
      title: editState.theme.title,
      choiceList: editState.choiceList,
    });
  }, [editState, form]);

  return (
    <>
      <Select
        value={editState.theme.id == 0 ? null : editState.theme.id}
        style={{ width: 300 }}
        options={convertSelectOptions(themeList)}
        onChange={async (themeId) => {
          setEditState({
            theme: searchTheme(themeId, themeList),
            choiceList: await searchChoiceList(themeId),
          });
        }}
      />
      {/* FIX ME 選択されたThemeの削除ボタン実装 */}
      <Form
        form={form}
        name="validateOnly"
        layout="vertical"
        autoComplete="off"
        onFinish={updateThCh}
        initialValues={{
          themeId: 0,
          title: "",
          choiceList: [{ id: 0, choice: "" }],
        }}
      >
        <ThemeForm />
        <ChoiceListForm />
        <FormButtons form={form} submitText={"Update"} isResetButton={false} />
      </Form>
    </>
  );
};
