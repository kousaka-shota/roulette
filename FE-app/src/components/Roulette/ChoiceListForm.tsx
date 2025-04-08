import { Button, Form, Input } from "antd";
import { FC } from "react";
import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons";

export const ChoiceListForm: FC = () => {
  return (
    <Form.List name="choiceList" rules={[{ validator: validateChoiceList }]}>
      {(fields, { add, remove }, { errors }) => (
        <>
          {fields.map((field, index) => (
            <Form.Item
              {...formItemLayout}
              label={index === 0 ? "選択肢" : ""}
              required={false}
              key={field.key}
            >
              <Form.Item
                // inputの中にchoiceのデータを格納
                name={[field.name, "choice"]}
                validateTrigger={["onChange", "onBlur"]}
                rules={[
                  {
                    required: true,
                    whitespace: true,
                    message: "Please input choice or delete this field.",
                  },
                ]}
                noStyle
              >
                <Input placeholder="選択肢の名称" style={{ width: "60%" }} />
              </Form.Item>
              {fields.length > 1 && (
                <MinusCircleOutlined
                  className="dynamic-delete-button"
                  onClick={() => remove(field.name)}
                />
              )}
            </Form.Item>
          ))}
          <Form.Item>
            <Button
              type="dashed"
              onClick={() => add()}
              style={{ width: "60%" }}
              icon={<PlusOutlined />}
            >
              Add field
            </Button>
            <Form.ErrorList errors={errors} />
          </Form.Item>
        </>
      )}
    </Form.List>
  );
};

// 選択肢リストのバリデーション
const validateChoiceList = async (_: any, names: string[]) => {
  if (!names || names.length < 2) {
    return Promise.reject(new Error("At least 1 choice"));
  }
};

const formItemLayout = {
  labelCol: {
    xs: { span: 24 },
    sm: { span: 4 },
  },
  wrapperCol: {
    xs: { span: 24 },
    sm: { span: 20 },
  },
};
