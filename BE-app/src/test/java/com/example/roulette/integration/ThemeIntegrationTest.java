package com.example.roulette.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class ThemeIntegrationTest extends BaseIntegrationTest {

    @BeforeEach
    void setUp() throws Exception {
        clearDataBase();
        createThemes();
    }

    @Test
    @DisplayName("テーマを削除する")
    void testDeleteTheme() throws Exception {

        mockMvc.perform(delete("/theme/{themeId}", createdThemeId_1))
                .andExpect(status().isOk());
        mockMvc.perform(get("/theme/"))
                .andExpect(jsonPath("$.results.length()").value(1))
                .andExpect(jsonPath("$.results.[0].title").value(THEME_TITLE_2));

    }

    @Test
    @DisplayName("データを全件取得する")
    void testCreateTheme_GetAllTheme() throws Exception {

        mockMvc.perform(get("/theme/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.length()").value(2))
                .andExpect(jsonPath("$.results[0].id").value(createdThemeId_1))
                .andExpect(jsonPath("$.results[0].title").value(THEME_TITLE_1))
                .andExpect(jsonPath("$.results[1].id").value(createdThemeId_2))
                .andExpect(jsonPath("$.results[1].title").value(THEME_TITLE_2));

    }

}
