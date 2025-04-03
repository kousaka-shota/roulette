package com.example.roulette.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.example.roulette_api.controller.model.ChoiceDTO;
import com.example.roulette_api.controller.model.ChoiceForm;
import com.example.roulette_api.controller.model.ChoiceListDTO;
import com.example.roulette_api.controller.model.ChoiceListForm;

// IntegrationTestはJPAを用いるとDBがロックされるので並列処理ができない
// →build.gradleにシングルスレッドでの処理を追加することで可能になる
@SpringBootTest
@AutoConfigureMockMvc
public class ChoiceIntegrationTest extends BaseIntegrationTest {

    @BeforeEach
    void setUp() throws Exception {
        clearDataBase();
        createThemes();

        ChoiceListForm choiceForms = new ChoiceListForm(
                Arrays.asList(
                        new ChoiceForm("Choice 5"),
                        new ChoiceForm("Choice 6"),
                        new ChoiceForm("Choice 7"),
                        new ChoiceForm("Choice 8")));

        mockMvc.perform(post("/choiceList/{themeId}", createdThemeId_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(choiceForms)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.results[0].choice").value("Choice 5"))
                .andExpect(jsonPath("$.results[3].choice").value("Choice 8"))
                .andExpect(jsonPath("$.results[0].themeId").value(createdThemeId_1))
                .andExpect(jsonPath("$.results[3].themeId").value(createdThemeId_1));

    }

    @Test
    @DisplayName("指定したThemeIdのChoiceListを取得する")
    void testGetChoiceList() throws Exception {
        mockMvc.perform(get("/choiceList/{themeId}", createdThemeId_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.length()").value(4))
                .andExpect(jsonPath("$.results[0].choice").value("Choice 5"))
                .andExpect(jsonPath("$.results[3].choice").value("Choice 8"))
                .andExpect(jsonPath("$.results[0].themeId").value(createdThemeId_1))
                .andExpect(jsonPath("$.results[3].themeId").value(createdThemeId_1));

    }

    @Test
    @DisplayName("ChoiceListの更新 -削除、追加、文字列の書き換え")
    void testUpdateChoiceList() throws Exception {
        ChoiceListDTO dto = new ChoiceListDTO(
                Arrays.asList(
                        new ChoiceDTO(5, "Choice 5 Updated", createdThemeId_1),
                        // Remove
                        // new ChoiceDTO(6,"Choice 6",createdThemeId_1),
                        new ChoiceDTO(7, "Choice 7", createdThemeId_1),
                        new ChoiceDTO(8, "Choice 8", createdThemeId_1),
                        new ChoiceDTO(0, "Choice 9 New", createdThemeId_1)));

        mockMvc.perform(put("/choiceList/{themeId}", createdThemeId_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                // .andExpect(jsonPath("$.results.length()").value(4))
                .andExpect(jsonPath("$.results[0].id").value(5))
                // .andExpect(jsonPath("$.results[0].choice").value("Choice 5 Updated"))
                .andExpect(jsonPath("$.results[1].id").value(7))
                .andExpect(jsonPath("$.results[1].choice").value("Choice 7"))
                .andExpect(jsonPath("$.results[3].id").value(9))
                .andExpect(jsonPath("$.results[3].choice").value("Choice 9 New"));
    }

}
