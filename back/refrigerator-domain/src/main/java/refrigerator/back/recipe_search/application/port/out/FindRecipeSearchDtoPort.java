package refrigerator.back.recipe_search.application.port.out;


import refrigerator.back.recipe_search.application.domain.RecipeSearchCondition;
import refrigerator.back.recipe_search.application.dto.RecipeSearchDto;

import java.util.List;

public interface FindRecipeSearchDtoPort {
    List<RecipeSearchDto> search(RecipeSearchCondition condition, int page, int size);
}
