package refrigerator.back.ingredient.application.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.exception.IngredientExceptionType;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;


@Getter
@AllArgsConstructor
public enum IngredientStorageType {

    FREEZER("냉동"),
    FRIDGE("냉장"),
    SEASON("조미료"),
    ROOM("실온");

    private String type;

    @JsonCreator
    public static IngredientStorageType from(String sub) {
        for (IngredientStorageType tag : IngredientStorageType.values()) {
            if (tag.getType().equals(sub)) {
                return tag;
            }
        }

        throw new BusinessException(CHECK_INGREDIENT_STORAGE_METHOD);
    }

    @JsonValue
    public String getType() {
        return type;
    }

}
