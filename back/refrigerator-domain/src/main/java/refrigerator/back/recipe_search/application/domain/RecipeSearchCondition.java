package refrigerator.back.recipe_search.application.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import refrigerator.back.myscore.application.domain.ScoreScope;

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
