package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.application.dto.MyIngredientDto;
import refrigerator.back.recipe.application.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.dto.RecipeDto;
import refrigerator.back.recipe.application.dto.RecipeIngredientDto;
import refrigerator.back.recipe.application.port.in.FindRecipeInfoUseCase;
import refrigerator.back.recipe.application.port.out.FindMyIngredientTablePort;
import refrigerator.back.recipe.application.port.out.FindRecipeInfoPort;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeLookUpService implements FindRecipeInfoUseCase {

    private final FindRecipeInfoPort findRecipeInfoPort;
    private final FindMyIngredientTablePort findMyIngredientsPort;

    @Override
    public RecipeDto findRecipeDto(Long recipeId) {
        return findRecipeInfoPort.findRecipeDto(recipeId);
    }

    @Override
    public List<RecipeCourseDto> findRecipeCourseDtoList(Long recipeId) {
        return findRecipeInfoPort.findRecipeCourseDtos(recipeId);
    }

    @Override
    public List<RecipeIngredientDto> findRecipeIngredientDtoList(Long recipeId, String memberId) {
        Map<String, MyIngredientDto> myIngredients = findMyIngredientsPort.findMyIngredients(memberId);
        List<RecipeIngredientDto> recipeIngredients = findRecipeInfoPort.findRecipeIngredientDtos(recipeId);
        recipeIngredients.forEach(recipeIngredient ->
                recipeIngredient.match(myIngredients.getOrDefault(recipeIngredient.getName(), null))
        );
        return recipeIngredients;
    }
}
