package com.example.roulette.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.roulette.entity.ChoiceEntity;
import com.example.roulette.repository.ChoiceRepository;
import com.example.roulette.service.ChoiceService;
import com.example.roulette_api.controller.model.ChoiceDTO;
import com.example.roulette_api.controller.model.ChoiceForm;

class ChoiceServiceTest {

    @Mock
    private ChoiceRepository choiceRepo;

    @InjectMocks
    private ChoiceService choiceSer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("指定したthemeIdのChoiceListが取得できる")
    void testSelectChoiceList() throws Exception {
        List<ChoiceEntity> entityRepo = Arrays.asList(
                new ChoiceEntity(1, "Choice 1", 1),
                new ChoiceEntity(2, "Choice 2", 1),
                new ChoiceEntity(3, "Choice 3", 1),
                new ChoiceEntity(4, "Choice 4", 1),
                new ChoiceEntity(5, "Choice 5", 1));
        when(choiceRepo.findByThemeId(eq(1))).thenReturn(entityRepo);

        Integer themeId = 1;
        List<ChoiceEntity> entities = choiceSer.selectChoiceList(themeId);

        assertEquals(5, entities.size());
        assertEquals("Choice 3", entities.get(2).getChoice());
        assertEquals("Choice 1", entities.get(0).getChoice());
        assertEquals(1, entities.get(4).getThemeId());
        assertEquals(3, entities.get(2).getId());

    }

    @Test
    @DisplayName("ChoiceListの新規登録")
    void testInsertChoiceList() {
        Integer themeId = 1;
        List<ChoiceForm> forms = Arrays.asList(
                new ChoiceForm("Choice1"),
                new ChoiceForm("Choice2"),
                new ChoiceForm("Choice3"),
                new ChoiceForm("Choice4"),
                new ChoiceForm("Choice5"));

        List<ChoiceEntity> result = choiceSer.insertChoiceList(themeId, forms);

        List<ChoiceEntity> expected = Arrays.asList(
                new ChoiceEntity(null, "Choice1", 1),
                new ChoiceEntity(null, "Choice2", 1),
                new ChoiceEntity(null, "Choice3", 1),
                new ChoiceEntity(null, "Choice4", 1),
                new ChoiceEntity(null, "Choice5", 1));

        assertEquals(expected, result);
        verify(choiceRepo, times(1)).saveAll(expected);

    }

    @Test
    @DisplayName("指定したThemeIdのChoice全件の削除")
    void testDeleteChoiceList() {
        Integer themeId = 1;
        choiceSer.deleteChoiceList(themeId);
        verify(choiceRepo, times(1)).deleteByThemeId(themeId);
    }

    @Nested
    @DisplayName("ChoiceListUpdateに用いるメソッドのテスト")
    public class testUpdateChoiceList_Nested {
        @Test
        @DisplayName("Choice一件の新規登録")
        void testInsertChoice() {
            Integer themeId = 1;
            String choice = "Choice1";
            choiceSer.insertChoice(themeId, choice);
            verify(choiceRepo, times(1)).save(new ChoiceEntity(null, choice, themeId));
        }

        @Test
        @DisplayName("指定したChoiceIdのChoice一件を削除する")
        void testDeleteChoice() {
            Integer choiceId = 1;
            choiceSer.deleteChoice(choiceId);
            verify(choiceRepo, times(1)).deleteById(choiceId);
        }

        @Test
        @DisplayName("実際にupdateする")
        void testUpdateChoiceList() {
            System.out.println("update");
            Integer themeId = 3;

            // prevのselectChoiceListの返り値の作成
            List<ChoiceEntity> prevEntitiesRepo = Arrays.asList(
                    new ChoiceEntity(1, "Choice 1", themeId),
                    new ChoiceEntity(2, "Choice 2", themeId),
                    new ChoiceEntity(3, "Choice 3", themeId),
                    new ChoiceEntity(4, "Choice 4", themeId));
            // 処理後のselectChoiceListの返り値の作成
            List<ChoiceEntity> expectedEntitiesRepo = Arrays.asList(
                    new ChoiceEntity(1, "Choice 1 Updated", themeId),
                    new ChoiceEntity(2, "Choice 2", themeId),
                    new ChoiceEntity(4, "Choice 4", themeId),
                    new ChoiceEntity(5, "Choice 5 New", themeId));
            // 二回呼び出してそれぞれ返り値が異なるため、二回分の返り値をセット
            when(choiceRepo.findByThemeId(themeId)).thenReturn(prevEntitiesRepo, expectedEntitiesRepo);

            when(choiceRepo.save(any(ChoiceEntity.class))).thenReturn(new ChoiceEntity());

            doNothing().when(choiceRepo).deleteById(anyInt());

            List<ChoiceDTO> dtoList = Arrays.asList(
                    new ChoiceDTO(1, "Choice 1 Updated", themeId),
                    new ChoiceDTO(2, "Choice 2", themeId),
                    new ChoiceDTO(4, "Choice 4", themeId),
                    new ChoiceDTO(0, "Choice 5 New", themeId));
            List<ChoiceEntity> result = choiceSer.updateChoiceList(themeId, dtoList);

            List<ChoiceEntity> expectedEntities = Arrays.asList(
                    new ChoiceEntity(1, "Choice 1 Updated", themeId),
                    new ChoiceEntity(2, "Choice 2", themeId),
                    new ChoiceEntity(4, "Choice 4", themeId),
                    new ChoiceEntity(5, "Choice 5 New", themeId));

            assertEquals(expectedEntities, result);
            verify(choiceRepo, times(2)).findByThemeId(themeId);
            verify(choiceRepo, times(1)).deleteById(3);
            verify(choiceRepo, times(1)).save(any(ChoiceEntity.class));

        }
    }

}
