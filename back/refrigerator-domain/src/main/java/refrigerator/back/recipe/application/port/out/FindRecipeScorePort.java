package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.application.domain.entity.RecipeScore;

public interface FindRecipeScorePort {
    RecipeScore findByRecipeId(Long recipeId);
}
