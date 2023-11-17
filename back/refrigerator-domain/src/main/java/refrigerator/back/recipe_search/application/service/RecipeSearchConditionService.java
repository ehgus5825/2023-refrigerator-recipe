package refrigerator.back.recipe_search.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.recipe.application.domain.value.RecipeDifficulty;
import refrigerator.back.recipe.application.domain.value.RecipeType;
import refrigerator.back.recipe_search.application.dto.RecipeSearchConditionsDto;
import refrigerator.back.recipe_search.application.port.in.FindSearchConditionUseCase;
import refrigerator.back.recipe_search.application.port.out.FindSearchConditionDataPort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeSearchConditionService implements FindSearchConditionUseCase {

    private final FindSearchConditionDataPort findSearchConditionDataPort;

    @Override
    public RecipeSearchConditionsDto findConditions() {
        return findSearchConditionDataPort.findConditionData();
    }
}
