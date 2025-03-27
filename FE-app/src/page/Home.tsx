import { Flex, Select } from "antd";
import { RouletteComp } from "@/components/Roulette";
import { getThemeList } from "@/api/theme/getThemeList";
import { useEffect, useState, CSSProperties } from "react";
import { ThemeListType } from "@/types/theme/themeList";
import { ThChModal } from "@/components/ThChModal";

export const Home = () => {
  const [themeList, setThemeList] = useState<ThemeListType>([]);
  const [selectedThemeId, setSelectedThemeId] = useState<number>(0);
  const [isRefresh, setIsRefresh] = useState<boolean>(false);
  useEffect(() => {
    const fetchThemeList = async () => {
      const { results } = await getThemeList();
      setThemeList(results);
      setSelectedThemeId(0);
    };
    fetchThemeList();
  }, [isRefresh]);

  return (
    <>
      <Flex gap="middle" vertical align="center">
        <Flex style={boxStyle} justify="center" align="center" gap="middle">
          <Select
            value={selectedThemeId == 0 ? null : selectedThemeId}
            style={{ width: 300 }}
            options={convertSelectOptions(themeList)}
            onChange={(id) => setSelectedThemeId(id)}
          />
          <ThChModal
            setIsRefresh={() => setIsRefresh(!isRefresh)}
            themeList={themeList}
          />
        </Flex>
        <RouletteComp selectedThemeId={selectedThemeId} />
      </Flex>
    </>
  );
};

const convertSelectOptions = (themeList: ThemeListType) => {
  const themeOptions = themeList.map(({ id, title }) => {
    return { value: id, label: title };
  });
  return themeOptions;
};

const boxStyle: CSSProperties = {
  width: "100%",
  height: 120,
  borderRadius: 6,
};
