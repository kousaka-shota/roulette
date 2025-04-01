package com.example.roulette.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.roulette.repository.theme.ThemeRepository;
import com.example.roulette_api.controller.model.ThemeForm;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class ThemeIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ThemeRepository themeRepo;


    @BeforeEach
    void setUp() throws Exception{
        themeRepo.deleteAllTheme();
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

    }

    @Test
    @DisplayName("データを全件取得する")
    void testCreateTheme_GetAllTheme() throws Exception{
        
        mockMvc.perform(get("/theme/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.results.length()").value(2))
        .andExpect(jsonPath("$.results[0].id").value(3))
        .andExpect(jsonPath("$.results[1].id").value(4))
        .andExpect(jsonPath("$.results[0].title").value("New Theme 3"))
        .andExpect(jsonPath("$.results[1].title").value("New Theme 4"));

    }

    @Test
    @DisplayName("テーマを削除する")
    void testDeleteTheme() throws Exception{
        String path = "/theme/";
        Integer themeId = 3;
        mockMvc.perform(delete(path + "{themeId}",themeId))
        .andExpect(status().isOk());

        mockMvc.perform(get(path))
        .andExpect(jsonPath("$.results.length()").value(1))
        .andExpect(jsonPath("$.results.[0].id").value(4));

    }
}
