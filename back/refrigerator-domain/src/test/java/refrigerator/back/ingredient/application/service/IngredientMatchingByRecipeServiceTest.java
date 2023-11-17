package refrigerator.back.ingredient.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;
import refrigerator.back.ingredient.application.port.out.recipeIngredient.FindRecipeIngredientPort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientMatchingByRecipeServiceTest {

    @InjectMocks IngredientMatchingByRecipeService ingredientMatchingByRecipeService;

    @Mock FindRecipeIngredientPort findRecipeIngredientPort;

    @Mock FindIngredientListPort findIngredientListPort;

    @Mock CurrentTime<LocalDate> currentTime;

    @Test
    @DisplayName("레시피 식재료 ID 반환 테스트")
    void getIngredientIdsTest(){

        LocalDate now = LocalDate.of(2023, 1, 1);

        String memberId = "email123@gmail.com";

        given(currentTime.now())
                .willReturn(now);

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .registrationDate(now.minusDays(10))
                .image(1)
                .email("email123@gmail.com")
                .capacityUnit("g")
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FRIDGE)
                .deleted(false);

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(builder.id(1L).name("감자").expirationDate(now.plusDays(1)).build());
        ingredientList.add(builder.id(2L).name("고구마").expirationDate(now.plusDays(1)).build());
        ingredientList.add(builder.id(3L).name("자색고구마").expirationDate(now.minusDays(1)).build());
        ingredientList.add(builder.id(4L).name("옥수수").expirationDate(now.minusDays(1)).build());

        given(findIngredientListPort.getIngredients(memberId))
                .willReturn(ingredientList);

        List<RecipeIngredientDto> recipeIngredientDtos = new ArrayList<>();

        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(5L).name("감자").build());
        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(6L).name("고구마").build());
        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(7L).name("자색고구마").build());
        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(8L).name("옥수수").build());

        given(findRecipeIngredientPort.getRecipeIngredient(1L))
                .willReturn(recipeIngredientDtos);

        List<Long> ingredientIds = ingredientMatchingByRecipeService.getIngredientIds(memberId, 1L);
        assertThat(ingredientIds.size()).isEqualTo(2);
        assertThat(ingredientIds.get(0)).isEqualTo(5L);
        assertThat(ingredientIds.get(1)).isEqualTo(6L);
    }

    @Test
    @DisplayName("사용할 수 있는 식재료의 이름, map으로 반환")
    void extractAvailableIngredientNames() {

        LocalDate now = LocalDate.of(2023, 1, 1);

        given(currentTime.now())
                .willReturn(now);

        Ingredient.IngredientBuilder builder = Ingredient.builder()
                .registrationDate(now.minusDays(10))
                .image(1)
                .email("email123@gmail.com")
                .capacityUnit("g")
                .capacity(30.0)
                .storageMethod(IngredientStorageType.FRIDGE)
                .deleted(false);

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(builder.id(1L).name("감자").expirationDate(now.plusDays(1)).build());
        ingredientList.add(builder.id(2L).name("고구마").expirationDate(now.plusDays(1)).build());
        ingredientList.add(builder.id(3L).name("자색고구마").expirationDate(now.minusDays(1)).build());
        ingredientList.add(builder.id(4L).name("옥수수").expirationDate(now.minusDays(1)).build());

        Map<String, Boolean> stringBooleanMap = ingredientMatchingByRecipeService.extractAvailableIngredientNames(ingredientList);
        assertThat(stringBooleanMap.size()).isEqualTo(2);
        assertThat(stringBooleanMap.get("감자")).isTrue();
        assertThat(stringBooleanMap.get("고구마")).isTrue();

    }
}