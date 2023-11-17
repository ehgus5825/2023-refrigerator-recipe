package refrigerator.back.recipe_search.application.port.in;


import refrigerator.back.recipe_search.application.dto.RecipeSearchDto;
import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;

import java.util.List;

public interface SearchRecipeUseCase {
    List<RecipeSearchDto> search(RecipeSearchCondition condition, int page, int size);
}
