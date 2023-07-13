package refrigerator.back.recipe_recommend.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.recipe.application.domain.value.RecipeIngredientType;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;
import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredient;
import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredientMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RecommendRecipeMatchingServiceTest {

    @Test
    @DisplayName("매칭률이 높은 상위 5개의 레시피 식별자 추출")
    void getMatchingRecipeIdsTest1() {
        // given
        RecommendRecipeMatchingService service = new RecommendRecipeMatchingService(
                getRecipeIngredients(),
                getMyIngredients("name1", "unit1", 62.0));
        // when
        Map<Long, Double> result = service.getRecipeMatchingPercent();
        // then
        assertEquals(1, result.size());
        assertEquals(60.0, result.get(1L));
    }

    @Test
    @DisplayName("매칭률이 높은 상위 5개의 레시피 식별자 추출")
    void getMatchingRecipeIdsTest2() {
        // given
        RecommendRecipeMatchingService service = new RecommendRecipeMatchingService(
                getRecipeIngredients(),
                getMyIngredients("name3", "unit3", 40.0));
        // when
        Map<Long, Double> result = service.getRecipeMatchingPercent();
        // then
        assertEquals(1, result.size());
        assertEquals(25.0, result.get(2L));
    }

    @Test
    @DisplayName("매칭률이 높은 상위 5개의 레시피 식별자 추출 -> 매칭된 레시피가 없을 때")
    void getMatchingRecipeIdsTest3() {
        // given
        RecommendRecipeMatchingService service = new RecommendRecipeMatchingService(
                getRecipeIngredients(),
                getMyIngredients("name10", "unit10", 40.0));
        // when
        Map<Long, Double> result = service.getRecipeMatchingPercent();
        // then
        assertEquals(0, result.size());
    }

    private Map<Long, RecommendRecipeIngredientMap> getRecipeIngredients(){
        RecommendRecipeIngredient item1 = getRecipeIngredientDto(1L, "name1", RecipeIngredientType.MAIN, 50.0, "unit1");
        RecommendRecipeIngredient item2 = getRecipeIngredientDto(1L, "name2", RecipeIngredientType.SUB, 51.0, "unit2");
        RecommendRecipeIngredient item3 = getRecipeIngredientDto(2L, "name3", RecipeIngredientType.SEASONING, 30.0, "unit3");
        RecommendRecipeIngredient item4 = getRecipeIngredientDto(2L, "name4", RecipeIngredientType.MAIN, 36.0, "unit4");
        Map<String, RecommendRecipeIngredient> recipe1 = new HashMap<>();
        recipe1.put(item1.getName(), item1);
        recipe1.put(item2.getName(), item2);
        Map<String, RecommendRecipeIngredient> recipe2 = new HashMap<>();
        recipe2.put(item3.getName(), item3);
        recipe2.put(item4.getName(), item4);
        Map<Long, RecommendRecipeIngredientMap> result = new HashMap<>();
        result.put(1L, new RecommendRecipeIngredientMap(recipe1));
        result.put(2L, new RecommendRecipeIngredientMap(recipe2));
        return result;
    }

    private RecommendRecipeIngredient getRecipeIngredientDto(long recipeId, String name, RecipeIngredientType type, double volume, String unit) {
        return RecommendRecipeIngredient.builder()
                .recipeId(recipeId)
                .name(name)
                .type(type)
                .volume(volume)
                .unit(unit).build();
    }

    private List<MyIngredientDto> getMyIngredients(String name, String unit, double volume){
        return Arrays.asList(
                MyIngredientDto.builder()
                        .name(name)
                        .unit(unit)
                        .volume(volume).build()
        );
    }
}