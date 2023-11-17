package refrigerator.back.mybookmark.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkDto;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkPreviewDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, MyBookmarkSelectQueryRepository.class})
@TestDataInit("mybookmark.sql")
class MyBookmarkSelectQueryRepositoryTest {

    @Autowired MyBookmarkSelectQueryRepository queryRepository;

    @Test
    @DisplayName("북마크 미리보기 목록 조회")
    void selectMyBookmarkPreviews() {
        // given
        String memberId = "jktest101@gmail.com";
        LocalDateTime preDate = LocalDateTime.of(2023, 7, 2, 1, 1);
        int size = 5;
        // when
        List<OutMyBookmarkPreviewDto> result = queryRepository.selectMyBookmarkPreviews(memberId, PageRequest.of(0, size));
        // then
        assertEquals(size, result.size());
        /* 최신순으로 정렬이 되었는지 확인 */
        for (OutMyBookmarkPreviewDto dto : result) {
            assertTrue(preDate.compareTo(dto.getCreateDateTime()) >= 0);
        }
    }

    @Test
    @DisplayName("나의 북마크 목록 조회")
    void selectMyBookmarks() {
        // given
        String memberId = "jktest101@gmail.com";
        LocalDateTime preDate = LocalDateTime.of(2023, 7, 2, 1, 1);
        int size = 8;
        // when
        List<OutMyBookmarkDto> result = queryRepository.selectMyBookmarks(memberId, PageRequest.of(0, size));
        assertEquals(size, result.size());
        /* 최신순으로 정렬이 되었는지 확인 */
        for (OutMyBookmarkDto dto : result) {
            assertTrue(preDate.compareTo(dto.getCreateDateTime()) >= 0);
        }
    }

}