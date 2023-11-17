package refrigerator.back.recipe.application.domain.entity;

import lombok.*;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.myscore.application.domain.ScoreScope;
import refrigerator.back.recipe.exception.RecipeExceptionType;

import javax.persistence.*;

@Entity
@Table(name = "recipe_score")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeScore extends ScoreScope {

    @Id
    @Column(name = "recipe_id")
    @Getter
    private Long recipeId;

    @Column(name = "person", nullable = false)
    private int person;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Getter
    @Column(name = "score_avg", nullable = false)
    private Double scoreAvg;

    private RecipeScore(Long recipeId, int person, Double amount) {
        this.recipeId = recipeId;
        this.person = person;
        this.amount = amount;
    }

    public static RecipeScore create(Long recipeId, int person, Double amount){
        RecipeScore recipeScore = new RecipeScore(recipeId, person, amount);
        recipeScore.toCalculateScoreAvg();
        return recipeScore;
    }

    public void addUp(double newScore){
        checkScoreScope(newScore);
        this.person += 1;
        this.amount += newScore;
        toCalculateScoreAvg();
    }

    public void renew(double oldScore, double newScore){
        checkScoreScope(newScore);
        if (person <= 0){
            throw new BusinessException(RecipeExceptionType.NOT_FOUND_RECIPE_SCORE_PERSON);
        }
        this.amount += (newScore - oldScore);
        toCalculateScoreAvg();
    }

    protected void toCalculateScoreAvg(){
        scoreAvg = person > 0 ? amount / person : 0.0;
    }

}
