package refrigerator.back.ingredient.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.ingredient.application.domain.entity.Ingredient;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;
import refrigerator.back.ingredient.application.port.out.FindSubIngredientPort;
import refrigerator.back.ingredient.application.port.out.DeleteIngredientPort;
import refrigerator.back.ingredient.application.port.out.SaveIngredientPort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientUpdateServiceTest {

    @InjectMocks IngredientUpdateService ingredientUpdateService;

    @Mock
    FindSubIngredientPort findSubIngredientPort;

    @Mock DeleteIngredientPort deleteIngredientPort;

    @Mock SaveIngredientPort saveIngredientPort;

    @Test
    @DisplayName("식재료 수정")
    void modifyIngredientTest() {

        LocalDate now = LocalDate.of(2023,1,1);

        // given
        Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .name("감자")
                .registrationDate(now)
                .expirationDate(now)
                .capacity(30.0)
                .capacityUnit("g")
                .image(1)
                .deleted(false)
                .storageMethod(IngredientStorageType.FRIDGE)
                .email("email123@gmail.com")
                .build();

        given(findSubIngredientPort.getIngredient(1L))
                .willReturn(ingredient);

        given(saveIngredientPort.saveIngredient(ingredient))
                .willReturn(1L);

        ingredientUpdateService.modifyIngredient(1L, now.plusDays(1),
                40.0, IngredientStorageType.FREEZER);

        Ingredient findIngredient = findSubIngredientPort.getIngredient(1L);

        assertThat(findIngredient.getCapacity()).isEqualTo(40);
        assertThat(findIngredient.getExpirationDate()).isEqualTo(now.plusDays(1));
        assertThat(findIngredient.getStorageMethod()).isEqualTo(IngredientStorageType.FREEZER);
    }

    @Test
    @DisplayName("식재료_삭제")
    void removeIngredientTest() {

        willDoNothing().given(deleteIngredientPort).deleteIngredient(2L);

        ingredientUpdateService.removeIngredient(2L);

        verify(deleteIngredientPort, times(1)).deleteIngredient(2L);
    }

    @Test
    @DisplayName("식재료 일괄 삭제")
    void removeAllIngredientsTest() {

        List<Long> ids = new ArrayList<>(List.of(1L, 2L, 3L, 4L, 5L, 6L));

        willDoNothing().given(deleteIngredientPort).deleteAllIngredients(ids);

        ingredientUpdateService.removeAllIngredients(ids);

        verify(deleteIngredientPort, times(1)).deleteAllIngredients(ids);
    }
}