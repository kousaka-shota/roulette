package com.example.roulette.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.roulette.entity.ChoiceEntity;
import com.example.roulette.repository.ChoiceRepository;
import com.example.roulette_api.controller.model.ChoiceDTO;
import com.example.roulette_api.controller.model.ChoiceForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChoiceService {

    private final ChoiceRepository choiceRepo;

    public List<ChoiceEntity> selectChoiceList(Integer themeId) {
        return choiceRepo.findByThemeId(themeId);
    }

    public List<ChoiceEntity> insertChoiceList(Integer themeId, List<ChoiceForm> form) {
        // List<ChoiceEntity>に成形する
        List<ChoiceEntity> entityList = form
                .stream()
                .map(choice -> new ChoiceEntity(null, choice.getChoice(), themeId))
                .collect(Collectors.toList());
        choiceRepo.saveAll(entityList);
        return entityList;
    }

    public ChoiceEntity insertChoice(Integer themeId, String choice) {
        // choiceにthemeIdをつける
        choiceRepo.save(new ChoiceEntity(null, choice, themeId));
        return null;
    }

    public void deleteChoiceList(Integer themeId) {
        choiceRepo.deleteByThemeId(themeId);
    }

    public void deleteChoice(Integer id) {

        choiceRepo.deleteById(id);
    }

    public List<ChoiceEntity> updateChoiceList(Integer themeId, List<ChoiceDTO> dtoList) {
        // entityに詰め替える
        List<ChoiceEntity> entityList = dtoList.stream().map(
                dto -> new ChoiceEntity(dto.getId(), dto.getChoice(), dto.getThemeId())).collect(Collectors.toList());

        List<ChoiceEntity> prevEntityList = selectChoiceList(themeId);

        handleEntityUpdates(themeId, prevEntityList, entityList);

        return selectChoiceList(themeId);
    }

    public void handleEntityUpdates(
            Integer themeId,
            List<ChoiceEntity> prevEntityList,
            List<ChoiceEntity> entityList) {

        for (ChoiceEntity entity : entityList) {
            // idがない場合は、新規で登録する処理へ
            if (entity.getId() == 0) {
                insertChoice(themeId, entity.getChoice());
            } else if (prevEntityList.stream().anyMatch(item -> item.getId().equals(entity.getId()))) {
                Optional<ChoiceEntity> oldEntity = choiceRepo.findById(entity.getId());
                if (oldEntity.isEmpty()) {
                    // 何もしない
                } else if (!oldEntity.get().getChoice().equals(entity.getChoice())) {
                    oldEntity.get().setChoice(entity.getChoice());
                    choiceRepo.save(oldEntity.get());
                }
            }

        }
        // prevEntityListをloopしてentityList内にidがあるか判定し削除処理
        for (ChoiceEntity entity_loop : prevEntityList) {
            if (!entityList.stream().anyMatch(item -> item.getId().equals(entity_loop.getId()))) {
                System.out.println(entity_loop.getChoice());
                deleteChoice(entity_loop.getId());
            }
        }

        // 複雑な条件分岐を書くよりも全削除・全追加をした方がシンプルと判断してた
        // deleteChoiceList(themeId);
        // choiceRepo.insertChoiceList(entityList);
    }
}
