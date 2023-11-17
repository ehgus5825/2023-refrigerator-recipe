package refrigerator.back.myscore.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.myscore.application.domain.MyScore;
import refrigerator.back.myscore.outbound.dto.OutMyScoreNumberDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, MyScoreSelectQueryRepository.class})
class MyScoreSelectCountQueryRepositoryTest {

    @Autowired MyScoreSelectQueryRepository queryRepository;
    @Autowired TestEntityManager em;

    @Test
    @DisplayName("나의 별점 개수 조회(별점 존재 확인용) 쿼리 테스트")
    void selectMyScoreCountByMemberIdAndRecipeId() {
        // given
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 11, 4, 44);
        String memberId = "memberId";
        long recipeId = 1L;
        MyScore myScore = MyScore.createForTest(memberId, recipeId, 3.4, createDateTime);
        em.persistAndFlush(myScore);
        // when
        OutMyScoreNumberDto result = queryRepository.selectMyScoreCountByMemberIdAndRecipeId(memberId, recipeId);
        // then
        assertEquals(1, result.getNumber());
    }

    @Test
    @DisplayName("나의 별점 전체 개수 조회 쿼리 테스트")
    void selectMyScoreCountByMemberId(){
        // given
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 11, 4, 44);
        MyScore myScore1 = MyScore.createForTest("memberId1", 1L, 3.4, createDateTime);
        MyScore myScore2 = MyScore.createForTest("memberId2", 1L, 3.4, createDateTime);
        em.persistAndFlush(myScore1);
        em.persistAndFlush(myScore2);
        // when
        String memberId = "memberId1";
        OutMyScoreNumberDto result = queryRepository.selectMyScoreCountByMemberId(memberId);
        // then
        assertEquals(1, result.getNumber());
    }
}