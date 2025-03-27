import { Form } from "antd";
import {
  postChoiceList,
  postChoiceListType,
} from "@/api/choice/postChoiceList";
import { postTheme } from "@/api/theme/postTheme";
import { ThemeForm } from "@/components/ThemeForm";
import { ChoiceListForm } from "@/components/ChoiceListForm";
import { FormButtons } from "@/components/FormButtons";
import { NewFormValues } from "@/types/formValues";

export const ThChNewForm = ({
  closeThChModal,
  setIsRefresh,
}: {
  closeThChModal: () => void;
  setIsRefresh: () => void;
}) => {
  const [form] = Form.useForm<NewFormValues>();

  const createThCh = async ({
    title,
    choiceList,
  }: {
    title: string;
    choiceList: postChoiceListType;
  }) => {
    const postedTheme = await postTheme(title);
    await postChoiceList(choiceList, postedTheme.id);
    form.resetFields();
    closeThChModal();
    setIsRefresh();
  };
  return (
    <Form
      form={form}
      name="validateOnly"
      layout="vertical"
      autoComplete="off"
      onFinish={createThCh}
      initialValues={{
        title: "",
        choiceList: [{ choice: "" }, { choice: "" }, { choice: "" }],
      }}
    >
      <ThemeForm />
      <ChoiceListForm />
      <FormButtons form={form} submitText="Submit" isResetButton={true} />
    </Form>
  );
};
