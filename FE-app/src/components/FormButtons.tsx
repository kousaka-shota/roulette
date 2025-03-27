import { Button, Form, FormInstance, Space } from "antd";
import { FC, useEffect, useState } from "react";

interface FormButtonsProps {
  form: FormInstance;
  submitText: string;
  isResetButton: boolean;
}

export const FormButtons: FC<FormButtonsProps> = ({
  form,
  submitText,
  isResetButton = false,
}) => {
  return (
    <Form.Item>
      <Space>
        <SubmitButton form={form}>{submitText}</SubmitButton>
        {isResetButton && <Button htmlType="reset">Reset</Button>}
      </Space>
    </Form.Item>
  );
};

interface SubmitButtonProps {
  form: FormInstance;
}

const SubmitButton: FC<React.PropsWithChildren<SubmitButtonProps>> = ({
  form,
  children,
}) => {
  const [submittable, setSubmittable] = useState<boolean>(false);

  // Watch all values
  const values = Form.useWatch([], form);

  useEffect(() => {
    form
      .validateFields({ validateOnly: true })
      .then(() => setSubmittable(true))
      .catch(() => setSubmittable(false));
  }, [form, values]);

  return (
    <Button type="primary" htmlType="submit" disabled={!submittable}>
      {children}
    </Button>
  );
};
