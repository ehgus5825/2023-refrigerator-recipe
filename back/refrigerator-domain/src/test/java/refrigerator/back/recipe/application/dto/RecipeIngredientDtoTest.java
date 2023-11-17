package refrigerator.back.recipe.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeIngredientDtoTest {

    @Test
    @DisplayName("나의 식재료와 매치 상태 확인 테스트 -> 식재료명 동일, 사용자 식재료 용량 >= 레시피 식재료 용량, 단위 동일")
    void matchSuccessTest1() {
        // given
        RecipeIngredientDto recipeIngredient = new RecipeIngredientDto(1L, "사과", "5g", "개", "개", "주재료", null);
        MyIngredientDto myIngredient = new MyIngredientDto("사과", 6.0, "개");
        // when
        recipeIngredient.match(myIngredient);
        // then
        assertTrue(recipeIngredient.getIsOwned());
    }

    @Test
    @DisplayName("나의 식재료와 매치 상태 확인 테스트 -> 사용자 식재료 이름이 다를 때")
    void matchFailTest1() {
        // given
        RecipeIngredientDto recipeIngredient = new RecipeIngredientDto(1L, "사과", "5g", "개", "개", "주재료", null);
        MyIngredientDto myIngredient = new MyIngredientDto("청사과", 2.0, "개");
        // when
        recipeIngredient.match(myIngredient);
        // then
        assertFalse(recipeIngredient.getIsOwned());
    }

    @Test
    @DisplayName("나의 식재료와 매치 상태 확인 테스트 -> 사용자 식재료 용량이 적을 때")
    void matchFailTest2() {
        // given
        RecipeIngredientDto recipeIngredient = new RecipeIngredientDto(1L, "사과", "5g", "개", "개", "주재료", null);
        MyIngredientDto myIngredient = new MyIngredientDto("사과", 2.0, "개");
        // when
        recipeIngredient.match(myIngredient);
        // then
        assertFalse(recipeIngredient.getIsOwned());
    }

    @Test
    @DisplayName("나의 식재료와 매치 상태 확인 테스트 -> 사용자 식재료 용량이 적을 때 && 단위가 맞지 않을 때")
    void matchFailTest3() {
        // given
        RecipeIngredientDto recipeIngredient = new RecipeIngredientDto(1L, "사과", "5g", "개", "개", "주재료", null);
        MyIngredientDto myIngredient = new MyIngredientDto("사과", 2.0, "쪽");
        // when
        recipeIngredient.match(myIngredient);
        // then
        assertFalse(recipeIngredient.getIsOwned());
    }
}