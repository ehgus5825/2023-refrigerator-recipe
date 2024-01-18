package refrigerator.back.comment.outbound.repository.query;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static refrigerator.back.comment.application.domain.entity.QComment.comment;
import static refrigerator.back.comment.application.domain.entity.QCommentHeart.commentHeart;


@Component
@RequiredArgsConstructor
public class CommentBatchQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /**
     * 댓글 하트 일괄 삭제 쿼리 by 삭제 상태
     * @return 쿼리 결과 타입
     */
    public Long deleteCommentHeart() {
        long execute = jpaQueryFactory.delete(commentHeart)
                .where(commentHeart.deleteStatus.eq(true))
                .execute();
        em.flush();
        em.clear();

        return execute;
    }

    /**
     * 댓글 일괄 삭제 쿼리 by 삭제 상태
     * @return 쿼리 결과 타입
     */
    public Long deleteComment() {

        long execute = jpaQueryFactory.delete(comment)
                .where(comment.commentRecord.deletedState.eq(true))
                .execute();

        em.flush();
        em.clear();

        return execute;
    }

    /**
     *
     * @return
     */
    public List<Long> findDeletedComment() {
        return jpaQueryFactory.select(comment.commentId)
                .from(comment)
                .where(comment.commentRecord.deletedState.eq(true))
                .fetch();
    }
}