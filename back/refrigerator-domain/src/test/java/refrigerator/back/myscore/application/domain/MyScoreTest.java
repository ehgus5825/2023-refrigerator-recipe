package refrigerator.back.myscore.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.myscore.application.service.RecipeScoreModifyHandler;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MyScoreTest {

    @Mock RecipeScoreModifyHandler handler;

    @Test
    @DisplayName("별점 생성 성공 테스트")
    void createSuccessTest(){
        // given
        double score = 3.2;
        long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 9, 1, 1);
        BDDMockito.doNothing().when(handler).addUp(recipeId, score);
        // when & then
        assertDoesNotThrow(() -> MyScore.create("memberId", recipeId, score, createDateTime, handler));
    }

    @Test
    @DisplayName("별점 생성 실패 테스트 -> 별점 범위 오류, 레시피 별점은 0.0 초과 5.0 이하 범주만 생성 가능")
    void createFailTest(){
        // given
        double score1 = 0.0;
        double score2 = 6.5;
        long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 9, 1, 1);
        // when & then
        assertThrows(BusinessException.class, () -> MyScore.create("memberId", recipeId, score1, createDateTime, handler));
        assertThrows(BusinessException.class, () -> MyScore.create("memberId", recipeId, score2, createDateTime, handler));
    }

    @Test
    @DisplayName("별점 수정 성공 테스트")
    void updateSuccessTest(){
        // given
        double score = 3.2;
        double newScore = 4.3;
        long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 9, 1, 1);
        BDDMockito.doNothing().when(handler).renew(recipeId, score, newScore);
        // when & then
        MyScore myScore = MyScore.create("memberId", recipeId, score, createDateTime, handler);
        assertDoesNotThrow(() -> myScore.update(newScore, handler));
    }

    @Test
    @DisplayName("별점 수정 실패 테스트 -> 별점 범위 오류")
    void updateFailTest(){
        // given
        double score = 3.2;
        double newScore = 6.5;
        long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 9, 1, 1);
        // when & then
        MyScore myScore = MyScore.create("memberId", recipeId, score, createDateTime, handler);
        assertThrows(BusinessException.class, () -> myScore.update(newScore, handler));
    }


}