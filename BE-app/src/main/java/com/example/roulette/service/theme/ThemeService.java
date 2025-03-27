package com.example.roulette.service.theme;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.roulette.repository.theme.ThemeRecord;
import com.example.roulette.repository.theme.ThemeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public List<ThemeEntity> getAllTheme(){
        List<ThemeRecord> recordList = themeRepository.selectAllTheme();
        List<ThemeEntity> themeListEntity = recordList.stream().map(record ->{
            return new ThemeEntity(record.getId(),record.getTitle());
        }).collect(Collectors.toList());
        return themeListEntity;
    }

    public ThemeEntity createTheme(String title){
        ThemeRecord record = new ThemeRecord(999, title);
        themeRepository.insertTheme(record);
        ThemeEntity entity = new ThemeEntity(record.getId(), record.getTitle());
        return entity;
    }

    public void deleteTheme(Integer themeId){
        themeRepository.deleteTheme(themeId);
    }


}
