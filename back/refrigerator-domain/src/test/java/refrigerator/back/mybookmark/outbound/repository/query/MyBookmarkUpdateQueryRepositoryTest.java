package refrigerator.back.mybookmark.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.jpa.WriteQueryResultType;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.mybookmark.application.domain.MyBookmark;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, MyBookmarkUpdateQueryRepository.class})
class MyBookmarkUpdateQueryRepositoryTest {

    @Autowired MyBookmarkUpdateQueryRepository queryRepository;
    @Autowired TestEntityManager em;

    @Test
    @DisplayName("북마크 삭제 상태 수정 쿼리 : 정상 -> 삭제")
    void updateMyBookmarkDeletedByIdSuccessTest1() {
        // given
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 10, 10, 20);
        MyBookmark myBookmark = MyBookmark.createForTest("memberId", 1L, false, createDateTime);
        Long bookmarkId = em.persistAndFlush(myBookmark).getBookmarkId();
        // when
        WriteQueryResultType result = queryRepository.updateMyBookmarkDeletedStatusById(bookmarkId, true);
        // then
        assertEquals(WriteQueryResultType.NORMAL, result);
        assertEquals(true, em.find(MyBookmark.class, bookmarkId).getDeleted());
    }

    @Test
    @DisplayName("북마크 삭제 상태 수정 쿼리 : 삭제 -> 정상")
    void updateMyBookmarkDeletedByIdSuccessTest2() {
        // given
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 10, 10, 20);
        MyBookmark myBookmark = MyBookmark.createForTest("memberId", 1L, true, createDateTime);
        Long bookmarkId = em.persistAndFlush(myBookmark).getBookmarkId();
        // when
        WriteQueryResultType result = queryRepository.updateMyBookmarkDeletedStatusById(bookmarkId, false);
        // then
        assertEquals(WriteQueryResultType.NORMAL, result);
        assertEquals(false, em.find(MyBookmark.class, bookmarkId).getDeleted());
    }
}