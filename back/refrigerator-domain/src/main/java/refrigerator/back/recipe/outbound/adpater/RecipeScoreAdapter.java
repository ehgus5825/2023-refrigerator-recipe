package refrigerator.back.recipe.outbound.adpater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.recipe.application.domain.entity.RecipeScore;
import refrigerator.back.recipe.application.port.out.FindRecipeScorePort;
import refrigerator.back.recipe.exception.RecipeExceptionType;
import refrigerator.back.recipe.outbound.repository.jpa.RecipeScoreRepository;

@Repository
@RequiredArgsConstructor
public class RecipeScoreAdapter implements FindRecipeScorePort {

    private final RecipeScoreRepository jpaRepository;

    @Override
    public RecipeScore findByRecipeId(Long recipeId) {
        return jpaRepository.findById(recipeId).orElseThrow(
                () -> new BusinessException(RecipeExceptionType.NOT_FOUND_RECIPE_SCORE)
        );
    }

}
