package refrigerator.back.recipe_search.application.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.myscore.application.domain.ScoreScope;
import refrigerator.back.recipe.exception.RecipeExceptionType;

@Getter
@Builder
@EqualsAndHashCode
public class RecipeSearchCondition extends ScoreScope {
    private String searchWord;
    private String recipeType;
    private String recipeFoodType;
    private String difficulty;
    private String category;
    private Double score;

    public void parameterCheck() {
        checkScoreScope(score);
    }
}
