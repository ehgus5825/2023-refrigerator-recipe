package refrigerator.back.mybookmark.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.mybookmark.application.domain.MyBookmark;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkNumberDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, MyBookmarkSelectQueryRepository.class})
class MyBookmarkSelectCountQueryRepositoryTest {

    @Autowired MyBookmarkSelectQueryRepository queryRepository;
    @Autowired TestEntityManager em;

    @Test
    @DisplayName("나의 북마크 전체 개수 조회")
    void selectNumberOfMyBookmarks() {
        // given
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 10, 1, 1);
        MyBookmark myBookmark1 = MyBookmark.createForTest("memberId1", 1L, false, createDateTime);
        MyBookmark myBookmark2 = MyBookmark.createForTest("memberId2", 1L, false, createDateTime);
        em.persistAndFlush(myBookmark1);
        em.persistAndFlush(myBookmark2);
        // when
        String memberId = "memberId1";
        OutMyBookmarkNumberDto result = queryRepository.selectNumberOfMyBookmarks(memberId);
        // then
        assertEquals(1, result.getNumber());
    }

    @Test
    @DisplayName("나의 북마크 개수 조회 -> 북마크 존재 확인용")
    void selectNumberOfMyBookmark() {
        // given
        String memberId = "memberId";
        long recipeId = 1L;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 10, 1, 1);
        MyBookmark myBookmark = MyBookmark.createForTest(memberId, recipeId, false, createDateTime);
        em.persistAndFlush(myBookmark);
        // when
        OutMyBookmarkNumberDto result = queryRepository.selectNumberOfMyBookmarkByRecipeIdAndMemberId(recipeId, memberId);
        // then
        assertEquals(1, result.getNumber());
    }
}