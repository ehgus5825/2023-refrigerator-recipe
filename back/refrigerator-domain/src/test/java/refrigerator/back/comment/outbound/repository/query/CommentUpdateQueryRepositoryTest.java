package refrigerator.back.comment.outbound.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.comment.outbound.repository.jpa.CommentHeartJpaRepository;
import refrigerator.back.comment.outbound.repository.jpa.CommentJpaRepository;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.global.jpa.WriteQueryResultType;
import refrigerator.back.global.jpa.config.QuerydslConfig;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, CommentUpdateQueryRepository.class, CommentHeartJpaRepository.class})
class CommentUpdateQueryRepositoryTest {

    @Autowired CommentUpdateQueryRepository query;
    @Autowired TestEntityManager em;

    @Test
    @DisplayName("댓글 내용 수정 쿼리 성공 테스트")
    void updateCommentToContentSuccessTest() {
        // given
        LocalDateTime createDateTime = LocalDateTime.of(2023, 6, 27, 10, 59);
        Comment comment = Comment.write(1L, "email", "content", createDateTime);
        Long commentId = em.persistAndGetId(comment, Long.class);
        // when
        LocalDateTime now = LocalDateTime.of(2023, 6, 27, 11, 59);
        String updateContent = "update content";
        WriteQueryResultType result = query.updateCommentToContent(comment.getCommentId(), updateContent, now);
        // then
        Comment expectedComment = em.find(Comment.class, commentId);
        assertEquals(WriteQueryResultType.NORMAL, result);
        assertEquals(updateContent, expectedComment.getContent());
        assertEquals(now, expectedComment.getCommentRecord().isModified());
    }

    @Test
    @DisplayName("댓글 좋아요 개수 증가 쿼리 성공 테스트")
    void updateCommentToCountSuccessTest1(){
        // given
        long commentId = 1L;
        CommentHeart commentHeart = new CommentHeart(commentId);
        Long commentHeartId = em.persistAndGetId(commentHeart, Long.class);
        // when
        WriteQueryResultType result = query.updateCommentToCount(commentId, 1);
        assertEquals(WriteQueryResultType.NORMAL, result);
        assertEquals(1, em.find(CommentHeart.class, commentHeartId).getCount());
    }

    @Test
    @DisplayName("댓글 좋아요 개수 감소 쿼리 실패 테스트")
    void updateCommentToCountSuccessTest2(){
        // given
        long commentId = 1L;
        CommentHeart commentHeart = new CommentHeart(commentId);
        Long commentHeartId = em.persistAndGetId(commentHeart, Long.class);
        // when
        WriteQueryResultType result = query.updateCommentToCount(commentId, -1);
        assertEquals(WriteQueryResultType.FAIL, result);
        assertEquals(0, em.find(CommentHeart.class, commentHeartId).getCount());
    }
}