import { Form, Input } from "antd";
import { FC } from "react";

export const ThemeForm: FC = () => {
  return (
    <>
      {/* themeIdはupdate時に使うので保持 */}
      <Form.Item name="themeId" hidden>
        <Input type="hidden" />
      </Form.Item>
      <Form.Item
        name="title"
        label="Title"
        rules={[{ required: true, message: "タイトルを入力して下さい" }]}
      >
        <Input />
      </Form.Item>
    </>
  );
};
