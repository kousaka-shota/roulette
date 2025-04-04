package com.example.roulette.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.roulette.repository.theme.ThemeRepository;
import com.example.roulette.service.theme.ThemeEntity;
import com.example.roulette.service.theme.ThemeService;

public class ThemeServiceTest {

    @Mock
    private ThemeRepository themeRepo;

    @InjectMocks
    private ThemeService themeSer;

    // テスト実行前にMockitoを初期化
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("テーマのリストを取得")
    void testGetAllTheme() throws Exception {
        List<ThemeEntity> entitiesRepo = Arrays.asList(
                new ThemeEntity(1, "Theme 1"),
                new ThemeEntity(2, "Theme 2"),
                new ThemeEntity(3, "Theme 3"),
                new ThemeEntity(4, "Theme 4"));

        when(themeRepo.findAll()).thenReturn(entitiesRepo);

        List<ThemeEntity> entities = themeSer.getAllTheme();

        assertEquals(4, entities.size());
        assertEquals(1, entities.get(0).getId());
        assertEquals("Theme 1", entities.get(0).getTitle());
        assertEquals(4, entities.get(3).getId());
        assertEquals("Theme 4", entities.get(3).getTitle());

    }

    @Test
    @DisplayName("テーマを新規作成")
    void testCreateTheme() throws Exception {

        // 戻り値を持たないのでdoNothingを用いる
        when(themeRepo.save(any(ThemeEntity.class))).thenReturn(new ThemeEntity(999, "New Theme"));

        ThemeEntity entity = themeSer.createTheme("New Theme");

        assertEquals(999, entity.getId());
        assertEquals("New Theme", entity.getTitle());

    }
}
