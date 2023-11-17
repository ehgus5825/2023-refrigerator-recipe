package refrigerator.back.recipe.application.port.in;

import refrigerator.back.recipe.application.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.dto.RecipeDto;
import refrigerator.back.recipe.application.dto.RecipeIngredientDto;

import java.util.List;

public interface FindRecipeInfoUseCase {
    RecipeDto findRecipeDto(Long recipeId);
    List<RecipeCourseDto> findRecipeCourseDtoList(Long recipeId);
    List<RecipeIngredientDto> findRecipeIngredientDtoList(Long recipeId, String memberId);
}
