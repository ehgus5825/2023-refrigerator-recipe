package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.application.domain.entity.RecipeScore;

public interface ModifyRecipeScorePort {
    Long modifyRecipeScore(RecipeScore recipeScore);
}
