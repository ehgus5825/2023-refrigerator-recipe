package refrigerator.back.ingredient.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientRegisterDTO;
import refrigerator.back.ingredient.application.port.out.ingredient.update.SaveIngredientPort;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientSaveServiceTest {

    @InjectMocks IngredientSaveService ingredientSaveService;

    @Mock SaveIngredientPort saveIngredientPort;

    @Mock CurrentTime<LocalDate> currentTime;

    @Test
    @DisplayName("식재료 등록")
    void registeredIngredientTest() {

        LocalDate now = LocalDate.of(2023,1,1);

        given(currentTime.now())
                .willReturn(now);

        given(saveIngredientPort.saveIngredient(any()))
                .willReturn(1L);

        IngredientRegisterDTO dto = ingredientSaveService.registerIngredient(
                "감자",
                now,
                30.0,
                "g",
                IngredientStorageType.FRIDGE,
                1,
                "email123@gmail.com"
        );

        assertThat(dto.getIngredientID()).isEqualTo(1L);
    }
}