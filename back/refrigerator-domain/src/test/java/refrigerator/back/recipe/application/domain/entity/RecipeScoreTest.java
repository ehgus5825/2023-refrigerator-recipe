package refrigerator.back.recipe.application.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.global.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.*;

class RecipeScoreTest {

    @Test
    @DisplayName("레시피 생성 테스트 -> 생성과 동시에 평균 계산")
    void createSuccessTest(){
        RecipeScore recipeScore1 = RecipeScore.create(1L, 10, 50.0);
        assertEquals(5.0, recipeScore1.getScoreAvg());

        RecipeScore recipeScore2 = RecipeScore.create(1L, 0, 0.0);
        assertEquals(0.0, recipeScore2.getScoreAvg());

    }

    @Test
    @DisplayName("새로운 별점 추가(합산) 성공 테스트 1")
    void addSuccessTest1() {
        double newScore = 4.0;
        RecipeScore recipeScore = RecipeScore.create(1L, 0, 0.0);
        recipeScore.addUp(newScore);
        recipeScore.addUp(newScore);
        assertEquals(4.0, recipeScore.getScoreAvg());
    }

    @Test
    @DisplayName("새로운 별점 추가(합산) 성공 테스트 2")
    void addSuccessTest2() {
        double newScore1 = 4.3;
        double newScore2 = 1.7;
        RecipeScore recipeScore = RecipeScore.create(1L, 0, 0.0);
        recipeScore.addUp(newScore1);
        recipeScore.addUp(newScore2);
        assertEquals(3.0, recipeScore.getScoreAvg());
    }

    @Test
    @DisplayName("새로운 별점 추가(합산) 실패 테스트 -> 별점 범위 오류")
    void addFailTest() {
        double score = 0.0;
        RecipeScore recipeScore = RecipeScore.create(1L, 0, 0.0);
        assertThrows(BusinessException.class, () -> recipeScore.addUp(score));
    }


    @Test
    @DisplayName("별점 갱신 성공 테스트")
    void renewSuccessTest() {
        double oldScore = 3.0;
        double newScore = 5.0;
        RecipeScore score = RecipeScore.create(1L, 10, 43.0);
        score.renew(oldScore, newScore);
        assertEquals(4.5, score.getScoreAvg());
    }

    @Test
    @DisplayName("별점 갱신 실패 테스트 -> 아무도 별점을 남기지 않은 별점을 갱신할 때")
    void renewFailTest() {
        double oldScore = 3.0;
        double newScore = 5.0;
        RecipeScore score = RecipeScore.create(1L, 0, 43.0);
        assertThrows(BusinessException.class, () -> score.renew(oldScore, newScore));
    }

}