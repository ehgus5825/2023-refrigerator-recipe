package refrigerator.back.recipe.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.recipe.outbound.dto.OutRecipeCourseDto;
import refrigerator.back.recipe.outbound.dto.OutRecipeDto;
import refrigerator.back.recipe.outbound.dto.OutRecipeIngredientDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@TestDataInit({"/recipe.sql", "/recipe_ingredient.sql"})
@Import({QuerydslConfig.class, RecipeSelectQueryRepository.class})
class RecipeSelectQueryRepositoryTest {

    @Autowired RecipeSelectQueryRepository queryRepository;

    @Test
    @DisplayName("레시피 Dto 조회")
    void selectRecipeDto() {
        Long recipeId = 1L;
        OutRecipeDto result = queryRepository.selectRecipeDto(recipeId);
        assertNotNull(result);
        assertNotEquals(OutRecipeDto.builder().build(), result);
    }

    @Test
    @DisplayName("레시피 식재료 Dto 목록 조회")
    void selectRecipeIngredientList() {
        Long recipeId = 1L;
        List<OutRecipeIngredientDto> result = queryRepository.selectRecipeIngredientList(recipeId);
        result.forEach(dto -> {
            assertNotNull(dto);
            assertNotEquals(OutRecipeIngredientDto.builder().build(), dto);
        });
    }

    @Test
    @DisplayName("레시피 과정 Dto 목록 조회")
    void selectRecipeCourseList() {
        Long recipeId = 1L;
        int preStep = 0;
        List<OutRecipeCourseDto> result = queryRepository.selectRecipeCourseList(recipeId);
        for (OutRecipeCourseDto dto : result) {
            assertNotNull(dto);
            assertTrue(dto.getStep() > preStep);
            preStep = dto.getStep();
        }
    }

}