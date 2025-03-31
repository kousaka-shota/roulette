package com.example.roulette.repository.theme;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ThemeRepository {

    @Select("SELECT id, title FROM theme")
    List<ThemeRecord> selectAllTheme();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO theme (title) VALUES(#{title})")
    void insertTheme(ThemeRecord record);

    @Delete("DELETE FROM theme WHERE id = #{themeId}")
    void deleteTheme(Integer themeId);

    @Delete("DELETE FROM theme")
    void deleteAllTheme();
}
