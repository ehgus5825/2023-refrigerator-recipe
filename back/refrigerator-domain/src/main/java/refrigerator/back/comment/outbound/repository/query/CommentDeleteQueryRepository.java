package refrigerator.back.comment.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.domain.QComment;
import refrigerator.back.global.jpa.WriteQueryResultType;

import javax.persistence.EntityManager;

import static refrigerator.back.comment.application.domain.QComment.comment;
import static refrigerator.back.comment.application.domain.QCommentHeart.commentHeart;

@Repository
@RequiredArgsConstructor
public class CommentDeleteQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    /**
     * 댓글 일괄 삭제 쿼리 by 삭제 상태
     * @return 쿼리 결과 타입
     */
    public WriteQueryResultType deleteCommentByDeletedStatus(){
        long result = jpaQueryFactory.delete(comment)
                .where(comment.commentRecord.deletedState.eq(true))
                .execute();
        em.flush();
        em.clear();
        return WriteQueryResultType.findTypeByResult(result);
    }

    /**
     * 댓글 하트 일괄 삭제 쿼리 by 삭제 상태
     * @return 쿼리 결과 타입
     */
    public WriteQueryResultType deleteCommentHeartByDeletedStatus(){
        long result = jpaQueryFactory.delete(commentHeart)
                .where(commentHeart.deleteStatus.eq(true))
                .execute();
        em.flush();
        em.clear();
        return WriteQueryResultType.findTypeByResult(result);
    }

}
