package com.example.roulette.integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.roulette.repository.choice.ChoiceRepository;
import com.example.roulette.repository.theme.ThemeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ThemeIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ThemeRepository themeRepo;
    
    @Autowired
    private ChoiceRepository choiceRepo;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp(){
        themeRepo.deleteAllTheme();
        choiceRepo.deleteAllChoice();
    }
}
