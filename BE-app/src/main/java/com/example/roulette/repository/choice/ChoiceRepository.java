package com.example.roulette.repository.choice;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.roulette.service.choice.ChoiceEntity;

@Mapper
public interface ChoiceRepository {
    
    @Select("SELECT id, choice, theme_id FROM luckyLoveLoveChoices WHERE theme_id = #{themeId}")
    List<ChoiceRecord> selectChoiceList(Integer themeId);
    
    // バルクインサート（一括登録）にしている。数百件の一括登録ならDBとのセッションが減らせるからよい。
    // 今回は遊びなのでこちらで実装。
    // 本来数十件位の実装なら単体で登録するメソッドをつくり、serviceでloopする方がシンプルでよい
    @Insert({
        "<script>",
        "INSERT INTO luckyLoveLoveChoices (choice, theme_id) VALUES ",
        "<foreach collection='entityList' item='item' separator=','>",
        "(#{item.choice}, #{item.themeId})",
        "</foreach>",
        "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertChoiceList(@Param("entityList") List<ChoiceEntity> entityList);
    
    // 複数の引数を持つ場合は、@Paramで指定する必要がある
    @Insert("INSERT INTO luckyLoveLoveChoices (choice, theme_id) VALUES (#{choice}, #{themeId})")
    void insertChoice(@Param("choice") String choice,@Param("themeId") Integer themeId);
    
    @Update("UPDATE luckyLoveLoveChoices SET choice = #{choice} WHERE id = #{id}")
    void updateChoice(ChoiceEntity entity);

    @Delete("DELETE FROM luckyLoveLoveChoices WHERE theme_id = #{themeId}")
    void deleteChoiceList(Integer themeId);

    @Delete("DELETE FROM luckyLoveLoveChoices WHERE id = #{id}")
    void deleteChoice(Integer id);


}
