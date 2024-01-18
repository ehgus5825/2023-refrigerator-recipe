package refrigerator.back.comment.outbound.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import refrigerator.back.annotation.DisabledRepositoryTest;
import refrigerator.back.comment.application.domain.entity.CommentHeart;
import refrigerator.back.global.jpa.config.QuerydslConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisabledRepositoryTest
@Import({QuerydslConfig.class, CommentHeartJpaRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentHeartJpaRepositoryTest {

    @Autowired CommentHeartJpaRepository commentHeartJpaRepository;

    @Test
    @DisplayName("댓글 좋아요 생성 및 조회 테스트")
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