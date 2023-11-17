package refrigerator.back.recipe.application.port.out;

import refrigerator.back.recipe.application.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.dto.RecipeDto;
import refrigerator.back.recipe.application.dto.RecipeIngredientDto;

import java.util.List;

public interface FindRecipeInfoPort {
    RecipeDto findRecipeDto(Long recipeId);
    List<RecipeIngredientDto> findRecipeIngredientDtos(Long recipeId);
    List<RecipeCourseDto> findRecipeCourseDtos(Long recipeId);
}
