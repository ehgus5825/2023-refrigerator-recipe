package refrigerator.back.recipe_search.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecipeSearchConditionsDto {

    private List<String> foodTypes;
    private List<String> foodCategories;
    private List<String> recipeTypes;
    private List<String> recipeLevels;

}
