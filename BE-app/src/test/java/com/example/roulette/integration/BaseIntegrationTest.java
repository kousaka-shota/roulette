package com.example.roulette.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.roulette.repository.choice.ChoiceRepository;
import com.example.roulette.repository.theme.ThemeRepository;
import com.example.roulette_api.controller.model.ThemeForm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// abstractをつけることで抽象クラスとなる。これはnewでインスタンスの生成ができない継承専用のクラス
// 関数の具体的な実装もできるし、実装を持たないメソッド（抽象メソッド）も作成できる
@Transactional
public abstract class BaseIntegrationTest {

    protected static final String THEME_TITLE_1 = "New Theme 1";
    protected static final String THEME_TITLE_2 = "New Theme 2";

    protected Integer createdThemeId_1;
    protected Integer createdThemeId_2;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    protected ThemeRepository themeRepo;

    @Autowired
    protected ChoiceRepository choiceRepo;

    protected void clearDataBase() throws Exception{
        themeRepo.deleteAll();
        choiceRepo.deleteAllChoice();
    }

    protected void createThemes() throws Exception{

        ThemeForm form_3 = new ThemeForm(THEME_TITLE_1);
        ThemeForm form_4 = new ThemeForm(THEME_TITLE_2);

        String response_1 = mockMvc.perform(post("/theme/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(form_3)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title").value(THEME_TITLE_1))
        .andReturn()
        .getResponse()
        .getContentAsString();

        // AutoIncrementされるIdを取得する
        JsonNode node_1 = mapper.readTree(response_1);
        createdThemeId_1 = node_1.path("id").asInt();

        String response_2 =  mockMvc.perform(post("/theme/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(form_4)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title").value(THEME_TITLE_2))
        .andReturn()
        .getResponse()
        .getContentAsString();

        JsonNode node_2 = mapper.readTree(response_2);
        createdThemeId_2 = node_2.path("id").asInt();



    }


}
