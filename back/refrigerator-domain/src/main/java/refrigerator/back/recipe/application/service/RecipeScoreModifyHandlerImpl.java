package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.application.domain.entity.RecipeScore;
import refrigerator.back.myscore.application.service.RecipeScoreModifyHandler;
import refrigerator.back.recipe.application.port.out.FindRecipeScorePort;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeScoreModifyHandlerImpl implements RecipeScoreModifyHandler {

    private final FindRecipeScorePort findRecipeScorePort;

    @Override
    public void addUp(Long recipeId, Double newScore) {
        RecipeScore recipeScore = findRecipeScorePort.findByRecipeId(recipeId);
        recipeScore.addUp(newScore);
    }

    @Override
    public void renew(Long recipeId, Double oldScore, Double newScore) {
        RecipeScore recipeScore = findRecipeScorePort.findByRecipeId(recipeId);
        recipeScore.renew(oldScore, newScore);
    }
}
