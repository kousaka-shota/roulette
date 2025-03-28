package com.example.roulette.choice;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.roulette.controller.choice.ChoiceController;
import com.example.roulette.service.choice.ChoiceEntity;
import com.example.roulette.service.choice.ChoiceService;
import com.example.roulette_api.controller.model.ChoiceForm;
import com.example.roulette_api.controller.model.ChoiceListForm;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ChoiceController.class)
public class ChoiceControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChoiceService choiceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /choiceList/{themeId} ")
    void testGetChoiceList() throws Exception{
        // 期待値作成
        List<ChoiceEntity> entities = Arrays.asList(
            new ChoiceEntity(1, "Choice One", 1),
            new ChoiceEntity(2, "Choice Two", 1),
            new ChoiceEntity(3, "Choice Three", 1));
        // 期待値セット
        when(choiceService.selectChoiceList(eq(1))).thenReturn(entities);
            
        Integer themeId = 1;
        // 実行させて実測値と期待値の比較 pathParamは第二引数で渡してあげる
        mockMvc.perform(get("/choiceList/{themeId}",themeId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.results.length()").value(3))
        .andExpect(jsonPath("$.results[0].id").value(1))
        .andExpect(jsonPath("$.results[1].choice").value("Choice Two"))
        .andExpect(jsonPath("$.results[2].themeId").value(1));
    }

    @Test
    @DisplayName("POST /choiceList/{themeId}")
    void testCreateChoiceList() throws Exception{
        // 期待値の作成とセット
        List<ChoiceEntity> entities = Arrays.asList(
            new ChoiceEntity(4, "Choice 4", 2),
            new ChoiceEntity(5, "Choice 5",2),
            new ChoiceEntity(6, "Choice 6", 2));
            
        when(choiceService.insertChoiceList(eq(2), anyList())).thenReturn(entities);
            
        // requestBody作成
        Integer themeId = 2;
        ChoiceListForm formList = new ChoiceListForm(
            Arrays.asList(
                new ChoiceForm("Choice 4"),
                new ChoiceForm("Choice 5"),
                new ChoiceForm("Choice 6")
                )
            );

        // 比較
        mockMvc.perform(post("/choiceList/{themeId}",themeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(formList)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.results.length()").value(3))
        .andExpect(jsonPath("$.results[0].id").value(4))
        .andExpect(jsonPath("$.results[1].choice").value("Choice 5"))
        .andExpect(jsonPath("$.results[2].themeId").value(2));

    }

    @Test
    @DisplayName("PUT /choiceList/{themeId}")
    void testUpdateChoiceList(){

    }
}
