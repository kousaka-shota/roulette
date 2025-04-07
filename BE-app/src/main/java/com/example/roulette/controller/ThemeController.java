package com.example.roulette.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.roulette.entity.ThemeEntity;
import com.example.roulette.service.ThemeService;
import com.example.roulette_api.controller.ThemeApi;
import com.example.roulette_api.controller.model.ThemeDTO;
import com.example.roulette_api.controller.model.ThemeForm;
import com.example.roulette_api.controller.model.ThemeListDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ThemeController implements ThemeApi {

    private final ThemeService themeService;

    @Override
    public ResponseEntity<ThemeListDTO> getAllTheme() {
        List<ThemeEntity> themeListEntity = themeService.getAllTheme();
        ThemeListDTO dto = new ThemeListDTO();
        List<ThemeDTO> dtoList = themeListEntity.stream().map(
                entity -> {
                    ThemeDTO themeDTO = new ThemeDTO(entity.getId(), entity.getTitle());
                    return themeDTO;
                }).collect(Collectors.toList());
        dto.setResults(dtoList);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<ThemeDTO> createTheme(@Valid @RequestBody ThemeForm form) {
        ThemeEntity entity = themeService.createTheme(form.getTitle());
        ThemeDTO dto = new ThemeDTO(entity.getId(), entity.getTitle());
        return ResponseEntity.created(URI.create("/themes/" + dto.getId())).body(dto);
    }

    @Override
    public ResponseEntity<Void> deleteTheme(Integer themeId) {
        themeService.deleteTheme(themeId);
        return ResponseEntity.ok().build();
    }

}
