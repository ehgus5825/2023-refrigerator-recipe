package refrigerator.back.recipe_search.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe.application.domain.entity.RecipeCategory;
import refrigerator.back.recipe.application.domain.entity.RecipeFoodType;
import refrigerator.back.recipe.application.domain.value.RecipeDifficulty;
import refrigerator.back.recipe.application.domain.value.RecipeType;
import refrigerator.back.recipe_search.application.dto.RecipeSearchConditionsDto;
import refrigerator.back.recipe_search.outbound.repository.RecipeSearchSelectQueryRepository;
import refrigerator.back.recipe_search.application.port.out.FindSearchConditionDataPort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecipeSearchConditionAdapter implements FindSearchConditionDataPort {

    private final RecipeSearchSelectQueryRepository repository;

    @Override
    public RecipeSearchConditionsDto findConditionData() {
        return RecipeSearchConditionsDto.builder()
                .recipeTypes(getRecipeCategoryCond())
                .foodCategories(getRecipeCategoryCond())
                .recipeLevels(getRecipeDifficultyCond())
                .foodTypes(getRecipeFoodTypeCond())
                .build();
    }

    private List<String> getRecipeTypeCond() {
        return Arrays.stream(RecipeType.values())
                .map(RecipeType::getName)
                .collect(Collectors.toList());
    }

    private List<String> getRecipeDifficultyCond() {
        return Arrays.stream(RecipeDifficulty.values())
                .map(RecipeDifficulty::getLevelName)
                .collect(Collectors.toList());
    }

    private List<String> getRecipeFoodTypeCond() {
        return repository.selectRecipeFoodTypes()
                .stream().map(RecipeFoodType::getTypeName)
                .collect(Collectors.toList());
    }

    private List<String> getRecipeCategoryCond() {
        return repository.selectRecipeCategories()
                .stream().map(RecipeCategory::getCategoryName)
                .collect(Collectors.toList());
    }
}
