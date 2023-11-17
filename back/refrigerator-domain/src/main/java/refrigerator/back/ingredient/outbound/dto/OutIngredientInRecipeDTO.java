package refrigerator.back.ingredient.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class OutIngredientInRecipeDTO {
    private Long ingredientId;
    private String name;

    @QueryProjection
    public OutIngredientInRecipeDTO(Long ingredientId, String name) {
        this.ingredientId = ingredientId;
        this.name = name;
    }
}
