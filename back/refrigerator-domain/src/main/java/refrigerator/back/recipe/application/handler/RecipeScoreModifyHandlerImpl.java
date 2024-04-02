package refrigerator.back.recipe.application.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.application.domain.entity.RecipeScore;
import refrigerator.back.myscore.application.handler.RecipeScoreModifyHandler;
import refrigerator.back.recipe.application.port.out.FindRecipeScorePort;
import refrigerator.back.recipe.application.port.out.ModifyRecipeScorePort;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeScoreModifyHandlerImpl implements RecipeScoreModifyHandler {

    private final FindRecipeScorePort findRecipeScorePort;
    private final ModifyRecipeScorePort modifyRecipeScorePort;

    @Override
    public void addUp(Long recipeId, Double newScore) {
        RecipeScore recipeScore = findRecipeScorePort.findByRecipeId(recipeId);
        recipeScore.addUp(newScore);

        modifyRecipeScorePort.modifyRecipeScore(recipeScore);
    }

    @Override
    public void renew(Long recipeId, Double oldScore, Double newScore) {
        RecipeScore recipeScore = findRecipeScorePort.findByRecipeId(recipeId);
        recipeScore.renew(oldScore, newScore);

        modifyRecipeScorePort.modifyRecipeScore(recipeScore);
    }
}
