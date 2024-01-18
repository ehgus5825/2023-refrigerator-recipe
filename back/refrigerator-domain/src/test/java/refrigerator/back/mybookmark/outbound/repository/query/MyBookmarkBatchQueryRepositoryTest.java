package refrigerator.back.mybookmark.outbound.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.exception.WriteQueryResultType;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.mybookmark.application.domain.MyBookmark;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, MyBookmarkBatchQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MyBookmarkBatchQueryRepositoryTest {

    @Autowired MyBookmarkBatchQueryRepository queryRepository;
    @Autowired TestEntityManager em;

    @Test
    @DisplayName("삭제 상태에 따른 북마크 삭제 쿼리 -> 1개의 데이터만 삭제하는 경우")
    void deleteMyBookmarkByDeletedStatusSuccessTest1() {
        // given
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 10, 10, 30);
        MyBookmark deletedBookmark = MyBookmark.createForTest("memberId", 1L, true, createDateTime);
        MyBookmark notDeletedBookmark = MyBookmark.createForTest("memberId", 1L, false, createDateTime);
        Long deletedId = em.persistAndGetId(deletedBookmark, Long.class);
        Long notDeletedId = em.persistAndGetId(notDeletedBookmark, Long.class);
        // when
        WriteQueryResultType result = queryRepository.deleteMyBookmarkByDeletedStatus();
        // then
        assertEquals(WriteQueryResultType.NORMAL, result);
        assertNull(em.find(MyBookmark.class, deletedId));
        assertNotNull(em.find(MyBookmark.class, notDeletedId));
    }

    @Test
    @DisplayName("삭제 상태에 따른 북마크 삭제 쿼리 -> 1개 이상의 데이터를 삭제하는 경우")
    void deleteMyBookmarkByDeletedStatusSuccessTest2() {
        // given
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 10, 10, 30);
        MyBookmark bookmark1 = MyBookmark.createForTest("memberId", 1L, true, createDateTime);
        MyBookmark bookmark2 = MyBookmark.createForTest("memberId", 2L, true, createDateTime);
        Long id1 = em.persistAndGetId(bookmark1, Long.class);
        Long id2 = em.persistAndGetId(bookmark2, Long.class);
        // when
        WriteQueryResultType result = queryRepository.deleteMyBookmarkByDeletedStatus();
        // then
        assertEquals(WriteQueryResultType.DUPLICATION, result);
        assertNull(em.find(MyBookmark.class, id1));
        assertNull(em.find(MyBookmark.class, id2));
    }


    @Test
    @DisplayName("MyBookmark 삭제 테스트")
    void deleteMyBookmarkByDeletedStatus() {

        String email = "email123@gmail.com";

        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        MyBookmark bookmark = MyBookmark
                .createForTest(email, 1L, true, now);

        em.persist(bookmark);

        assertThat(queryRepository.deleteMyBookmarkByDeletedStatus())
                .isEqualTo(WriteQueryResultType.NORMAL);
    }
}