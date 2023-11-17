package refrigerator.back.recipe_recommend.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.recipe.application.domain.value.RecipeIngredientType;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RecipeIngredientMapDtoTest {

    @Test
    @DisplayName("전체 식재료의 가중치 합 구하기")
    void getTypeWeightAmount() {
        RecommendRecipeIngredientMap mapDto = new RecommendRecipeIngredientMap(getRecipeIngredientMap());
        assertEquals(5, mapDto.getIngredientTypeWeightAmount());
    }

    @Test
    @DisplayName("나의 식재료와 동일한 레시피 식재료일 경우, 레시피 식재료의 가중치 가져오기")
    void getMatchedIngredientTypeWeightTest1() {
        // given
        MyIngredientDto case1 = MyIngredientDto.builder()
                .name("name1")
                .unit("unit1")
                .volume(82.0)
                .build();
        MyIngredientDto case2 = MyIngredientDto.builder()
                .name("name2")
                .unit("unit2")
                .volume(70.0)
                .build();
        // when
        RecommendRecipeIngredientMap mapDto = new RecommendRecipeIngredientMap(getRecipeIngredientMap());
        // then
        assertEquals(3, mapDto.getMatchedIngredientTypeWeight(case1));
        assertEquals(2, mapDto.getMatchedIngredientTypeWeight(case2));
    }


    @Test
    @DisplayName("나의 식재료와 레시피 식재료가 일치하지 않을 경우, 0을 반환")
    void getMatchedIngredientTypeWeightTest2() {
        // given
        MyIngredientDto case1 = MyIngredientDto.builder()
                .name("name3")
                .unit("unit3")
                .volume(82.0)
                .build();
        MyIngredientDto case2 = MyIngredientDto.builder()
                .name("name1")
                .unit("unit1")
                .volume(20.0)
                .build();
        // when
        RecommendRecipeIngredientMap mapDto = new RecommendRecipeIngredientMap(getRecipeIngredientMap());
        // then
        assertEquals(0, mapDto.getMatchedIngredientTypeWeight(case1));
        assertEquals(0, mapDto.getMatchedIngredientTypeWeight(case2));
    }


    private Map<String, RecommendRecipeIngredient> getRecipeIngredientMap(){
        RecommendRecipeIngredient item1 = getRecipeIngredientDto(1L, "name1", RecipeIngredientType.MAIN, 50.0, "unit1");
        RecommendRecipeIngredient item2 = getRecipeIngredientDto(1L, "name2", RecipeIngredientType.SUB, 51.0, "unit2");
        Map<String, RecommendRecipeIngredient> result = new HashMap<>();
        result.put(item1.getName(), item1);
        result.put(item2.getName(), item2);
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

}