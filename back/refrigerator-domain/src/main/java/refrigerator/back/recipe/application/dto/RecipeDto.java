package refrigerator.back.recipe.application.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Field;

@Getter
@Builder
@EqualsAndHashCode
public class RecipeDto {
    Long recipeId;
    String recipeName;
    String recipeImage;
    Double scoreAvg;
    String description;
    String cookingTime;
    String kcal;
    String servings;
    String difficulty;
}
