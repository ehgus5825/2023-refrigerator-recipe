package refrigerator.back.comment.outbound.repository.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.global.jpa.config.QuerydslConfig;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, CommentHeartJpaRepository.class})
class CommentHeartJpaRepositoryTest {

    @Autowired CommentHeartJpaRepository commentHeartJpaRepository;

    @Test
    void saveAndFindTest() {
        // given
        Long commentId = 1L;
        CommentHeart commentHeart = CommentHeart.createForTest(commentId, 0, false);
        // when
        commentHeartJpaRepository.save(commentHeart);
        // then
        Optional<CommentHeart> result = commentHeartJpaRepository.findById(commentId);
        assertTrue(result.isPresent());
    }

}