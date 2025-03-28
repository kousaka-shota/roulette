package com.example.roulette.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.roulette.repository.choice.ChoiceRepository;
import com.example.roulette.service.choice.ChoiceService;

public class ChoiceServiceTest {

    @Mock
    private ChoiceRepository choiceRepo;

    @InjectMocks
    private ChoiceService choiceSer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("")
    void testSelectChoiceList() throws Exception {

    }

    @Test
    @DisplayName("")
    void testInsertChoiceList() throws Exception {
        
    }

    @Test
    @DisplayName("")
    void testUpdateChoiceList() throws Exception {
        
    }



}
