package com.example.roulette.unit.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.roulette.controller.theme.ThemeController;
import com.example.roulette.service.theme.ThemeEntity;
import com.example.roulette.service.theme.ThemeService;
import com.example.roulette_api.controller.model.ThemeForm;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ThemeController.class)
public class ThemeControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ThemeService themeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /theme -成功時にテーマのリストを取得")
    void testGetAllTheme_Success() throws Exception {
        List<ThemeEntity> themeEntities = Arrays.asList(
            new ThemeEntity(1,"Theme One"),
            new ThemeEntity(2,"Theme Two")
        );
        // 期待値のセット
        when(themeService.getAllTheme()).thenReturn(themeEntities);

        mockMvc.perform(get("/theme/"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.results.length()").value(2))
        .andExpect(jsonPath("$.results[0].id").value(1))
        .andExpect(jsonPath("$.results[0].title").value("Theme One"));
    }
    @Nested
    @DisplayName("POST /theme")
    public class CreateThemeTest  {
        
        @Test
        @DisplayName("正常 -成功時にテーマを登録")
        void testCreateTheme_Success() throws Exception{
            // 期待値の作成
            ThemeEntity entity = new ThemeEntity(1, "Theme One");
            // 期待値のセット
            //eq()はまっちゃー関数でTheme Oneと同じ値が来たときはthenReturnのやつを返すってする（any()とかisInt()とかもまっちゃ関数） 
            when(themeService.createTheme(eq("Theme One"))).thenReturn(entity);
            
            // requestBodyとして渡すもの

            ThemeForm form = new ThemeForm("Theme One");
            // 実行し実測値と期待値の比較
                // contentにrequestBodyとして渡したいものをJsonにして渡す
            mockMvc.perform(post("/theme/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1));
        }

        @Test
        @DisplayName("異常 -タイトル空白時にバリデーション")
        void testCreateTheme_ValidationError() throws Exception{

            ThemeForm form = new ThemeForm("");
            // 実行後の実測値と期待値の比較
            mockMvc.perform(post("/theme/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(form)))
            .andExpect(status().isBadRequest());
        }
    }
}
