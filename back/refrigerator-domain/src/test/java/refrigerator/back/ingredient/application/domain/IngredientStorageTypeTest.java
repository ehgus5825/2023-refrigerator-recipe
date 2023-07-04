package refrigerator.back.ingredient.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BusinessException;

import static org.assertj.core.api.Assertions.*;

class IngredientStorageTypeTest {

    @Test
    @DisplayName("식재료 보관 enum 타입 테스트")
    void ingredientStorageTypeTest() {
        assertThat(IngredientStorageType.ROOM.getType()).isEqualTo("실온");
        assertThat(IngredientStorageType.FRIDGE.getType()).isEqualTo("냉장");
        assertThat(IngredientStorageType.FREEZER.getType()).isEqualTo("냉동");
        assertThat(IngredientStorageType.SEASON.getType()).isEqualTo("조미료");

        assertThat(IngredientStorageType.from("실온")).isEqualTo(IngredientStorageType.ROOM);
        assertThat(IngredientStorageType.from("냉장")).isEqualTo(IngredientStorageType.FRIDGE);
        assertThat(IngredientStorageType.from("냉동")).isEqualTo(IngredientStorageType.FREEZER);
        assertThat(IngredientStorageType.from("조미료")).isEqualTo(IngredientStorageType.SEASON);

        assertThatThrownBy(() -> IngredientStorageType.from("방치")).isInstanceOf(BusinessException.class);
    }
}