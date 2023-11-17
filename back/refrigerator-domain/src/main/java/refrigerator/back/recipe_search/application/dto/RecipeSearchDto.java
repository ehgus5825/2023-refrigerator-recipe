package refrigerator.back.recipe_search.application.dto;

import lombok.*;

import java.lang.reflect.Field;

@Getter
@Builder
@EqualsAndHashCode
public class RecipeSearchDto {
    private Long recipeId;
    private String recipeName;
    private String recipeImage;
    private Double scoreAvg;
    private Integer views;

}
