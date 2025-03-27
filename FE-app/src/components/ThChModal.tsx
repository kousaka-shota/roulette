import { Button, Modal } from "antd";
import { useState } from "react";
import { ThChNewForm } from "@/components/ThChNewForm";
import { ThChEditForm } from "./ThChEditForm";
import { ThemeListType } from "@/types/theme/themeList";

export const ThChModal = ({
  setIsRefresh,
  themeList,
}: {
  setIsRefresh: () => void;
  themeList: ThemeListType;
}) => {
  const [thChModalOpen, setThChModalOpen] = useState(false);
  const [isEdit, setIsEdit] = useState(false);

  const showThChModal = () => {
    setThChModalOpen(true);
  };

  const closeThChModal = () => {
    setThChModalOpen(false);
  };
  return (
    <>
      <Button type="primary" onClick={showThChModal}>
        Create
      </Button>
      <Button
        type="primary"
        onClick={() => {
          showThChModal();
          setIsEdit(true);
        }}
      >
        Edit
      </Button>
      <Modal
        title={`テーマの${isEdit ? "編集" : "新規作成"}`}
        open={thChModalOpen}
        onCancel={closeThChModal}
        footer={null}
      >
        {isEdit ? (
          <ThChEditForm
            closeThChModal={closeThChModal}
            setIsRefresh={setIsRefresh}
            themeList={themeList}
          />
        ) : (
          <ThChNewForm
            closeThChModal={closeThChModal}
            setIsRefresh={setIsRefresh}
          />
        )}
      </Modal>
    </>
  );
};
