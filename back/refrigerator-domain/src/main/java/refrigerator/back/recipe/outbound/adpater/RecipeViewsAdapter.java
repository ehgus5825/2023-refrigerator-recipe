package refrigerator.back.recipe.outbound.adpater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.exception.RecipeExceptionType;
import refrigerator.back.recipe.outbound.repository.query.RecipeUpdateQueryRepository;
import refrigerator.back.recipe.application.port.out.UpdateRecipeViewsPort;


@Repository
@RequiredArgsConstructor
public class RecipeViewsAdapter implements UpdateRecipeViewsPort {

    private final RecipeUpdateQueryRepository queryRepository;

    @Override
    public void add(Long recipeId) {
        queryRepository.updateRecipeViews(recipeId)
                .throwExceptionWhenNotAllowDuplicationResource(RecipeExceptionType.NOT_FOUND_RECIPE_VIEW);
    }

}
