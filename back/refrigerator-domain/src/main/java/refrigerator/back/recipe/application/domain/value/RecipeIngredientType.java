package refrigerator.back.recipe.application.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.exception.MappingException;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RecipeIngredientType {

    MAIN("주재료", 3),
    SUB("부재료", 2),
    SEASONING("양념", 1),
    NO_TYPE("타입없음", 0)
    ;

    private String typeName;
    private int weight;

    public static RecipeIngredientType getType(String name){
        return Arrays.stream(RecipeIngredientType.values())
                .filter(type -> type.getTypeName().equals(name))
                .findAny()
                .orElseGet(() -> NO_TYPE);
    }
}
