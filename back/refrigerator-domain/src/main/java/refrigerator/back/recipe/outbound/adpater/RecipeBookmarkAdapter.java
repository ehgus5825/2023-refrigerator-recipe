package refrigerator.back.recipe.outbound.adpater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.application.port.out.UpdateRecipeBookmarkPort;
import refrigerator.back.recipe.exception.RecipeExceptionType;
import refrigerator.back.recipe.outbound.repository.query.RecipeUpdateQueryRepository;

@Repository
@RequiredArgsConstructor
public class RecipeBookmarkAdapter implements UpdateRecipeBookmarkPort {

    private final RecipeUpdateQueryRepository queryRepository;

    @Override
    public void add(Long recipeId) {
        queryRepository.updateRecipeBookmarkCount(recipeId, 1)
                .throwExceptionWhenNotAllowDuplicationResource(RecipeExceptionType.NOT_FOUND_RECIPE_BOOKMARK);
    }

    @Override
    public void delete(Long recipeId) {
        queryRepository.updateRecipeBookmarkCount(recipeId, -1)
                .throwExceptionWhenNotAllowDuplicationResource(RecipeExceptionType.NOT_FOUND_RECIPE_BOOKMARK);
    }
}
