package com.example.roulette.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.roulette.entity.ThemeEntity;
import com.example.roulette.repository.ThemeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public List<ThemeEntity> getAllTheme() {
        return themeRepository.findAll();
    }

    public ThemeEntity createTheme(String title) {
        ThemeEntity entity = new ThemeEntity();
        entity.setTitle(title);
        return themeRepository.save(entity);
    }

    public void deleteTheme(Integer themeId) {
        if (themeRepository.existsById(themeId)) {
            themeRepository.deleteById(themeId);
        } else {
            throw new ResourceNotFoundException("Error: ThemeId:" + themeId + " is not Found");
        }
    }

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
