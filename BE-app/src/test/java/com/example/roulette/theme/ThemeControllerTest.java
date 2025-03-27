package com.example.roulette.theme;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.roulette.controller.theme.ThemeController;
import com.example.roulette.service.theme.ThemeEntity;
import com.example.roulette.service.theme.ThemeService;
import com.example.roulette_api.controller.model.ThemeListDTO;


@ExtendWith(MockitoExtension.class)
@DisplayName("ThemeControllerの単体テスト")
public class ThemeControllerTest {

    @Mock
    private ThemeService themeService; 

    @InjectMocks
    private ThemeController themeController;

    @Test
    @DisplayName("2件取得される場合のテスト")
    public void testGetAllTheme(){
        // Moc化したmethodから返ってくるデータを定義
        List<ThemeEntity> themeEntityList = Arrays.asList(new ThemeEntity(1, "Theme1"), new ThemeEntity(2, "Theme2"));
        when(themeService.getAllTheme()).thenReturn(themeEntityList);

        ResponseEntity<ThemeListDTO> response = themeController.getAllTheme();

        ThemeListDTO themeListDTO = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(themeListDTO.getResults().get(0).getTitle().equals("Theme1"));
    }
}
