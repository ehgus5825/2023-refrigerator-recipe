package refrigerator.back.recipe.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import refrigerator.back.recipe.adapter.in.web.RecipeOutputFormat;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDetailDTO extends RecipeOutputFormat {
    private Long recipeID;
    private String recipeName;
    private String servings;
    private String kcal;
    private String description;
    private String cookingTime;
    private String difficulty;
    private String image;
    private Double scoreAvg;
    private Double myScore;
    private Integer views;
    private Set<RecipeIngredientDTO> ingredients;
}
