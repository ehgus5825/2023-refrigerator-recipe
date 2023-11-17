package refrigerator.back.recipe_recommend.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.recipe.application.domain.value.RecipeIngredientType;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;

import static org.junit.jupiter.api.Assertions.*;

class RecipeIngredientDtoTest {

    @Test
    @DisplayName("소유한 식재료와 매칭이 될 때")
    void getMatchedIngredientTypeWeightTest1() {
        // given
        MyIngredientDto myIngredientDto = MyIngredientDto.builder()
                .name("name")
                .volume(60.2)
                .unit("unit").build();
        // when
        RecommendRecipeIngredient recipeIngredientDto = new RecommendRecipeIngredient(1L, "name", RecipeIngredientType.MAIN, 50.0, "unit");
        // then
        assertEquals(3, recipeIngredientDto.getMatchedIngredientTypeWeight(myIngredientDto));
    }

    @Test
    @DisplayName("소유한 식재료와 매칭이 안 될 때 -> 이름이 일치하지 않는 경우")
    void getMatchedIngredientTypeWeightTest2() {
        // given
        MyIngredientDto myIngredientDto = MyIngredientDto.builder()
                .name("otherName")
                .volume(60.2)
                .unit("unit").build();
        // when
        RecommendRecipeIngredient recipeIngredientDto = new RecommendRecipeIngredient(1L, "name", RecipeIngredientType.MAIN, 50.0, "unit");
        // then
        assertEquals(0, recipeIngredientDto.getMatchedIngredientTypeWeight(myIngredientDto));
    }

    @Test
    @DisplayName("소유한 식재료와 매칭이 안 될 때 -> 용량 조건을 만족하지 않는 경우")
    void getMatchedIngredientTypeWeightTest3() {
        // given
        MyIngredientDto myIngredientDto = MyIngredientDto.builder()
                .name("name")
                .volume(30.2)
                .unit("unit").build();
        // when
        RecommendRecipeIngredient recipeIngredientDto = new RecommendRecipeIngredient(1L, "name", RecipeIngredientType.MAIN, 50.0, "unit");
        // then
        assertEquals(0, recipeIngredientDto.getMatchedIngredientTypeWeight(myIngredientDto));
    }

    @Test
    @DisplayName("소유한 식재료와 매칭이 안 될 때 -> 단위가 일치하지 않는 경우")
    void getMatchedIngredientTypeWeightTest4() {
        // given
        MyIngredientDto myIngredientDto = MyIngredientDto.builder()
                .name("name")
                .volume(70.2)
                .unit("otherUnit").build();
        // when
        RecommendRecipeIngredient recipeIngredientDto = new RecommendRecipeIngredient(1L, "name", RecipeIngredientType.MAIN, 50.0, "unit");
        // then
        assertEquals(0, recipeIngredientDto.getMatchedIngredientTypeWeight(myIngredientDto));
    }
}