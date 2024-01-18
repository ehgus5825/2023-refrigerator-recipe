package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.domain.RecipeIngredientCollection;
import refrigerator.back.ingredient.application.port.in.MatchIngredientByRecipeUseCase;
import refrigerator.back.ingredient.application.port.out.FindRecipeIngredientPort;
import refrigerator.back.ingredient.application.port.out.FindSubIngredientPort;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientMatchingByRecipeService implements MatchIngredientByRecipeUseCase {

    private final FindRecipeIngredientPort findRecipeIngredientPort;
    private final FindSubIngredientPort findSubIngredientPort;
    private final CurrentTime<LocalDate> currentTime;

    @Override
    public List<Long> getIngredientIds(String memberId, Long recipeId) {

        RecipeIngredientCollection collection = new RecipeIngredientCollection(
                findRecipeIngredientPort.getRecipeIngredient(recipeId));

        return collection.getIds(findSubIngredientPort.getAvailableIngredients(memberId, currentTime.now()));
    }
}
