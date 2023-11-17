package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.application.port.in.matchByRecipe.MatchIngredientByRecipeUseCase;
import refrigerator.back.ingredient.application.port.out.recipeIngredient.FindRecipeIngredientPort;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;
import refrigerator.back.ingredient.exception.IngredientExceptionType;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;
import static refrigerator.back.ingredient.exception.IngredientExceptionType.NOT_FOUND_INGREDIENT;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientMatchingByRecipeService implements MatchIngredientByRecipeUseCase {

    private final FindRecipeIngredientPort findRecipeIngredientPort;
    private final FindIngredientListPort findIngredientListPort;
    private final CurrentTime<LocalDate> currentTime;

    @Override
    public List<Long> getIngredientIds(String memberId, Long recipeId) {

        Map<String, Boolean> nameMap = extractAvailableIngredientNames(
                findIngredientListPort.getIngredients(memberId));

        List<RecipeIngredientDto> recipeIngredient = findRecipeIngredientPort.getRecipeIngredient(recipeId);

        if (recipeIngredient.isEmpty()){
            throw new BusinessException(NOT_FOUND_REGISTERED_RECIPE);
        }

        return recipeIngredient.stream()
                .filter(item -> nameMap.getOrDefault(item.getName(), false))
                .map(RecipeIngredientDto::getIngredientId)
                .collect(Collectors.toList());
    }

    public Map<String, Boolean> extractAvailableIngredientNames(List<Ingredient> ingredients) {

        Map<String, Boolean> nameMap = ingredients.stream()
                .filter(i -> ChronoUnit.DAYS.between(currentTime.now(), i.getExpirationDate()) >= 0)
                .collect(Collectors.toMap(Ingredient::getName, ingredient -> true));

        return nameMap;
    }
}
