package refrigerator.back.recipe_search.application.port.out;

import refrigerator.back.recipe_search.application.dto.RecipeSearchConditionsDto;

public interface FindSearchConditionDataPort {
    RecipeSearchConditionsDto findConditionData();
}
