package refrigerator.back.comment.outbound.repository.query;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.comment.application.domain.entity.Comment;
import refrigerator.back.comment.application.domain.entity.CommentHeart;
import refrigerator.back.comment.application.domain.entity.CommentRecord;
import refrigerator.back.global.jpa.config.QuerydslConfig;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({QuerydslConfig.class, CommentBatchQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class CommentBatchQueryRepositoryTest {

    @Autowired
    CommentBatchQueryRepository commentBatchQueryRepository;

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("CommentHeart 삭제 테스트")
    void deleteCommentHeartTest(){

        CommentHeart commentHeart = new CommentHeart(1L, 0, true);

        em.persist(commentHeart);

        assertThat(commentBatchQueryRepository.deleteCommentHeart()).isEqualTo(1);
    }

    @Test
    @DisplayName("Comment 삭제 테스트")
    void deleteCommentTest(){
        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        String email = "email123@gmail.com";

        Comment comment = Comment.builder()
                .recipeId(1L)
                .writerId(email)
                .commentRecord(new CommentRecord(now, true, false))
                .content("aaaaaa")
                .build();

        em.persist(comment);

        assertThat(commentBatchQueryRepository.deleteComment()).isEqualTo(1);
    }

    @Test
    @DisplayName("삭제 상태가 true인 댓글 조회")
    void findDeletedCommentTest(){
        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        String email = "email123@gmail.com";

        Comment comment = Comment.builder()
                .recipeId(1L)
                .writerId(email)
                .commentRecord(new CommentRecord(now, true, false))
                .content("aaaaaa")
                .build();

        Long commentId = em.persistAndGetId(comment, Long.class);

        List<Long> commentIds = commentBatchQueryRepository.findDeletedComment();

        assertThat(commentIds.get(0)).isEqualTo(commentId);
    }
}