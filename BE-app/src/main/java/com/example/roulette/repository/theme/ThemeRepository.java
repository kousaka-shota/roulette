package com.example.roulette.repository.theme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roulette.service.theme.ThemeEntity;


@Repository
public interface ThemeRepository extends JpaRepository<ThemeEntity,Integer>{

}
