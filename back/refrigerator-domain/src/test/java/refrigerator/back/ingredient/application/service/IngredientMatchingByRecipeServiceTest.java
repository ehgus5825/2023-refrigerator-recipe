package refrigerator.back.ingredient.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.application.port.out.FindRecipeIngredientPort;
import refrigerator.back.ingredient.application.port.out.FindSubIngredientPort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientMatchingByRecipeServiceTest {
    
    @InjectMocks IngredientMatchingByRecipeService ingredientMatchingByRecipeService;

    @Mock FindRecipeIngredientPort findRecipeIngredientPort;

    @Mock FindSubIngredientPort findSubIngredientPort;

    @Mock CurrentTime<LocalDate> currentTime;

    @Test
    @DisplayName("레시피 식재료 ID 반환 테스트")
    void getIngredientIdsTest(){

        LocalDate now = LocalDate.of(2023, 1, 1);

        String memberId = "email123@gmail.com";

        given(currentTime.now())
                .willReturn(now);

        MyIngredientDTO.MyIngredientDTOBuilder builder = MyIngredientDTO.builder()
                .unit("g")
                .volume(30.0);

        List<MyIngredientDTO> ingredientList = new ArrayList<>();
        ingredientList.add(builder.id(1L).name("감자").build());
        ingredientList.add(builder.id(2L).name("고구마").build());
        ingredientList.add(builder.id(3L).name("자색고구마").build());
        ingredientList.add(builder.id(4L).name("옥수수").build());

        given(findSubIngredientPort.getAvailableIngredients(memberId,now))
                .willReturn(ingredientList);

        List<RecipeIngredientDto> recipeIngredientDtos = new ArrayList<>();

        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(5L).name("감자").build());
        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(6L).name("고구마").build());
        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(7L).name("자색고구마").build());
        recipeIngredientDtos.add(RecipeIngredientDto.builder().ingredientId(8L).name("옥수수").build());

        given(findRecipeIngredientPort.getRecipeIngredient(1L))
                .willReturn(recipeIngredientDtos);

        List<Long> ingredientIds = ingredientMatchingByRecipeService.getIngredientIds(memberId, 1L);
        assertThat(ingredientIds.size()).isEqualTo(4);
        assertThat(ingredientIds.get(0)).isEqualTo(5L);
        assertThat(ingredientIds.get(1)).isEqualTo(6L);
    }
}