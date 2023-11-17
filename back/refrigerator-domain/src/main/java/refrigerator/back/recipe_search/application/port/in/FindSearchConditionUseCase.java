package refrigerator.back.recipe_search.application.port.in;

import refrigerator.back.recipe_search.application.dto.RecipeSearchConditionsDto;

import java.util.List;

public interface FindSearchConditionUseCase {
    RecipeSearchConditionsDto findConditions();
}
