package refrigerator.back.ingredient.application.port.out.recipeIngredient;


import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;

import java.util.List;

public interface FindRecipeIngredientPort {
    List<RecipeIngredientDto> getRecipeIngredient(Long recipeId);
}
