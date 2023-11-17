package refrigerator.back.comment.outbound.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.annotation.TestDataInit;
import refrigerator.back.comment.outbound.dto.OutCommentDto;
import refrigerator.back.comment.application.domain.CommentSortCondition;
import refrigerator.back.comment.outbound.dto.OutCommentNumberDto;
import refrigerator.back.global.jpa.config.QuerydslConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, CommentSelectQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestDataInit(value = {"/comment.sql", "/member.sql"})
@Slf4j
class CommentSelectQueryRepositoryTest {

    @Autowired CommentSelectQueryRepository queryRepository;

    @Test
    @DisplayName("댓글 미리 보기 조회 성공 테스트")
    void selectPreviewComments() {
        int size = 3;
        List<OutCommentDto> comments = queryRepository.selectPreviewComments(1L, PageRequest.of(0, size));
        assertEquals(size, comments.size());
        assertTrue(isExist(comments));
    }

    @Test
    @DisplayName("댓글 전체 개수 구하기")
    void selectCommentsCount(){
        OutCommentNumberDto result = queryRepository.selectCommentsCount(1L);
        assertEquals(11, result.getNumber());
    }

    @Test
    @DisplayName("댓글 전체 하트순 조회 성공 테스트")
    void selectCommentsByHeartCountSuccessTest() {
        String memberId = "mstest102@gmail.com";
        int size = 5;
        List<OutCommentDto> comments = queryRepository.selectComments(1L, memberId, PageRequest.of(0, size), CommentSortCondition.HEART);
        assertEquals(size, comments.size());
        assertTrue(isExist(comments));
    }

    @Test
    @DisplayName("댓글 전체 최신순 조회 성공 테스트")
    void selectCommentsByDateSuccessTest() {
        // given
        String memberId = "mstest102@gmail.com";
        int size = 5;
        // when
        List<OutCommentDto> comments = queryRepository.selectComments(1L, memberId, PageRequest.of(0, size), CommentSortCondition.DATE);
        // then
        assertEquals(size, comments.size());
        assertTrue(isExist(comments));
    }

    @Test
    @DisplayName("내가 쓴 댓글 조회하기 성공 테스트")
    void selectMyComments() {
        // given
        String memberId = "jktest101@gmail.com";
        // when
        List<OutCommentDto> comments = queryRepository.selectMyComments(memberId, 1L, PageRequest.of(0, 11));
        // then
        assertTrue(isExist(comments));
    }

    private boolean isExist(List<OutCommentDto> comments){
        return comments.stream().noneMatch(comment -> comment.equals(OutCommentDto.builder().build()));
    }
}