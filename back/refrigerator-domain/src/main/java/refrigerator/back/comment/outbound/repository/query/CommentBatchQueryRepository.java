package refrigerator.back.comment.outbound.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static refrigerator.back.comment.application.domain.QCommentHeart.commentHeart;

@Component
@RequiredArgsConstructor
public class CommentBatchQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager em;

    @Transactional
    public void deleteCommentHeart(List<Long> ids) {

        jpaQueryFactory.delete(commentHeart)
                .where(commentHeart.commentId.in(ids))
                .execute();

        em.flush();
        em.clear();
    }

    @Transactional
    public void deleteCommentHeartPeople(Long id) {

    }
}
