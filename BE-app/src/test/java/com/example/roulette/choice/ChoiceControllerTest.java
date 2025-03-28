package com.example.roulette.choice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.roulette.controller.choice.ChoiceController;
import com.example.roulette.service.choice.ChoiceService;
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
    void testGetChoiceList(){

    }

    @Test
    @DisplayName("POST /choiceList/{themeId}")
    void testCreateChoiceList(){

    }

    @Test
    @DisplayName("PUT /choiceList/{themeId}")
    void testUpdateChoiceList(){
        
    }
}
