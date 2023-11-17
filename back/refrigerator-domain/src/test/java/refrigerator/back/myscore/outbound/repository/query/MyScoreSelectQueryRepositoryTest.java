package refrigerator.back.myscore.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.myscore.outbound.dto.OutMyScoreDetailDto;
import refrigerator.back.myscore.outbound.dto.OutMyScoreDto;
import refrigerator.back.myscore.outbound.dto.OutMyScorePreviewDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, MyScoreSelectQueryRepository.class})
@TestDataInit({"/recipe.sql", "/myscore.sql"})
class MyScoreSelectQueryRepositoryTest {

    @Autowired MyScoreSelectQueryRepository queryRepository;

    @Test
    @DisplayName("별점 상세 목록 조회 쿼리 테스트")
    void selectMyScores() {
        String memberId = "mstest102@gmail.com";
        List<OutMyScoreDetailDto> result = queryRepository.selectMyScoreDetailDtos(memberId, PageRequest.of(0, 11));
        LocalDateTime preDateTime = LocalDateTime.of(2025, 1, 1, 1, 1);
        for (OutMyScoreDetailDto dto : result) {
            assertNotEquals(OutMyScoreDetailDto.builder().build(), dto);
            assertTrue(preDateTime.compareTo(dto.getCreateDateTime()) >= 0);
            preDateTime = dto.getCreateDateTime();
        }
    }

    @Test
    @DisplayName("별점 단건 조회 쿼리 테스트")
    void selectMyScoreDto() {
        String memberId = "mstest102@gmail.com";
        Long recipeId = 1L;
        OutMyScoreDto result = queryRepository.selectMyScoreDto(recipeId, memberId);
        assertNotNull(result);
        assertEquals(3.5, result.getScore());
    }

    @Test
    @DisplayName("별점 미리보기 목록 조회 쿼리 테스트")
    void selectMyScorePreviews() {
        String memberId = "mstest102@gmail.com";
        List<OutMyScorePreviewDto> result = queryRepository.selectMyScorePreviewDtos(memberId, PageRequest.of(0, 11));
        LocalDateTime preDateTime = LocalDateTime.of(2025, 1, 1, 1, 1);
        for (OutMyScorePreviewDto dto : result) {
            assertNotEquals(OutMyScorePreviewDto.builder().build(), dto);
            assertTrue(preDateTime.compareTo(dto.getCreateDateTime()) >= 0);
            preDateTime = dto.getCreateDateTime();
        }
    }

}