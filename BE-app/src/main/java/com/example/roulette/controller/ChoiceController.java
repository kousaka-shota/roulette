package com.example.roulette.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.roulette.entity.ChoiceEntity;
import com.example.roulette.service.ChoiceService;
import com.example.roulette_api.controller.ChoiceApi;
import com.example.roulette_api.controller.model.ChoiceDTO;
import com.example.roulette_api.controller.model.ChoiceListDTO;
import com.example.roulette_api.controller.model.ChoiceListForm;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChoiceController implements ChoiceApi {

    private final ChoiceService choiceSer;

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ChoiceListDTO> getChoiceList(Integer themeId) {
        List<ChoiceEntity> entityList = choiceSer.selectChoiceList(themeId);
        return ResponseEntity.ok().body(toChoiceListDTO(entityList));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ChoiceListDTO> createChoiceList(
            Integer themeId,
            ChoiceListForm form) {
        // formをstring[]に書き換える
        List<ChoiceEntity> entityList = choiceSer.insertChoiceList(themeId, form.getChoiceList());
        return ResponseEntity.created(null).body(toChoiceListDTO(entityList));
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ChoiceListDTO> updateChoiceList(Integer themeId, ChoiceListDTO dto) {
        List<ChoiceEntity> entityList = choiceSer.updateChoiceList(themeId, dto.getResults());
        return ResponseEntity.ok().body(toChoiceListDTO(entityList));
    }

    public ChoiceListDTO toChoiceListDTO(List<ChoiceEntity> entityList) {
        List<ChoiceDTO> dtoList = entityList.stream().map(
                entity -> new ChoiceDTO(entity.getId(), entity.getChoice(), entity.getThemeId()))
                .collect(Collectors.toList());
        ChoiceListDTO dto = new ChoiceListDTO();
        dto.setResults(dtoList);
        return dto;
    }

}
