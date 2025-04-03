package com.example.roulette.repository.choice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roulette.service.choice.ChoiceEntity;

@Repository
public interface ChoiceRepository extends JpaRepository<ChoiceEntity, Integer> {

    // テーマIDでChoiceEntityを取得するメソッド(findByの後にエンティティのフィールド名をつけるとそれを条件にして検索してくれるようになる）
    List<ChoiceEntity> findByThemeId(Integer themeId);

    void deleteByThemeId(Integer themeId);

}
