import { Button, Flex, Statistic } from "antd";
import { Roulette, useRoulette } from "react-hook-roulette";
import { getChoiceList } from "@/api/choice/getChoiceList";
import { useEffect, useState } from "react";

type RouletteType = {
  id: number;
  name: string;
};

const initialItems = [
  {
    id: 0,
    name: "",
  },
];

export const RouletteComp = ({
  selectedThemeId,
}: {
  selectedThemeId: number;
}) => {
  const [items, setItems] = useState<RouletteType[]>(initialItems);
  useEffect(() => {
    const fetchChoiceList = async (themeId: number) => {
      if (themeId !== 0) {
        const { results } = await getChoiceList(themeId);
        const items: RouletteType[] = results.map((choice) => {
          return { id: choice.id, name: choice.choice };
        });
        setItems(items);
      }
    };
    fetchChoiceList(selectedThemeId);
  }, [selectedThemeId]);

  const { roulette, onStart, onStop, result } = useRoulette({
    items,
  });

  return (
    <>
      <Roulette roulette={roulette} />
      <Flex align="center" gap="middle">
        <Button onClick={onStart}>Start</Button>
        <Button onClick={onStop}>Stop</Button>
      </Flex>
      {result && <Statistic title="Result" value={result} />}
    </>
  );
};
