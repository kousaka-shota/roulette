package com.example.roulette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roulette.entity.ThemeEntity;

@Repository
public interface ThemeRepository extends JpaRepository<ThemeEntity, Integer> {

}
