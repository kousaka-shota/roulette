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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.roulette.repository.choice.ChoiceRepository;
import com.example.roulette.repository.theme.ThemeRepository;
import com.example.roulette_api.controller.model.ChoiceDTO;
import com.example.roulette_api.controller.model.ChoiceForm;
import com.example.roulette_api.controller.model.ChoiceListDTO;
import com.example.roulette_api.controller.model.ChoiceListForm;
import com.example.roulette_api.controller.model.ThemeForm;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ChoiceIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ThemeRepository themeRepo;

    @Autowired
    private ChoiceRepository choiceRepo;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() throws Exception{
        themeRepo.deleteAllTheme();
        choiceRepo.deleteAllChoice();
        ThemeForm form_3 = new ThemeForm("New Theme 3");
        ThemeForm form_4 = new ThemeForm("New Theme 4");

        mockMvc.perform(post("/theme/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(form_3)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(3))
        .andExpect(jsonPath("$.title").value("New Theme 3"));

        mockMvc.perform(post("/theme/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(form_4)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(4))
        .andExpect(jsonPath("$.title").value("New Theme 4"));

        ChoiceListForm choiceForms = new ChoiceListForm(
            Arrays.asList(
                new ChoiceForm("Choice 5"),
                new ChoiceForm("Choice 6"),
                new ChoiceForm("Choice 7"),
                new ChoiceForm("Choice 8")
                )
        );
        Integer themeId = 3;
        mockMvc.perform(post("/choiceList/{themeId}",themeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(choiceForms)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.results[0].id").value(5))
        .andExpect(jsonPath("$.results[0].choice").value("Choice 5"))
        .andExpect(jsonPath("$.results[3].choice").value("Choice 8"))
        .andExpect(jsonPath("$.results[0].themeId").value(3))
        .andExpect(jsonPath("$.results[3].themeId").value(3));

    }

    @Test
    @DisplayName("指定したThemeIdのChoiceListを取得する")
    void testGetChoiceList() throws Exception {
        Integer themeId = 3;
        mockMvc.perform(get("/choiceList/{themeId}",themeId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.results[0].id").value(5))
        .andExpect(jsonPath("$.results[0].choice").value("Choice 5"))
        .andExpect(jsonPath("$.results[3].choice").value("Choice 8"))
        .andExpect(jsonPath("$.results[0].themeId").value(3))
        .andExpect(jsonPath("$.results[3].themeId").value(3));
        
    }

    @Test
    @DisplayName("ChoiceListの更新 -削除、追加、文字列の書き換え")
    void testUpdateChoiceList() throws Exception{
        Integer themeId = 3;
        ChoiceListDTO dto = new ChoiceListDTO(
            Arrays.asList(
                new ChoiceDTO(5,"Choice 5 Updated",themeId),
                // Remove
                // new ChoiceDTO(6,"Choice 6",themeId),
                new ChoiceDTO(7,"Choice 7",themeId),
                new ChoiceDTO(8,"Choice 8",themeId),
                new ChoiceDTO(0,"Choice 9 New",themeId)
                )
        );

        mockMvc.perform(put("/choiceList/{themeId}",themeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.results.length()").value(4))
        .andExpect(jsonPath("$.results[0].id").value(5))
        .andExpect(jsonPath("$.results[0].choice").value("Choice 5 Updated"))
        .andExpect(jsonPath("$.results[1].id").value(7))
        .andExpect(jsonPath("$.results[1].choice").value("Choice 7"))
        .andExpect(jsonPath("$.results[3].id").value(9))
        .andExpect(jsonPath("$.results[3].choice").value("Choice 9 New"));
    }

}
