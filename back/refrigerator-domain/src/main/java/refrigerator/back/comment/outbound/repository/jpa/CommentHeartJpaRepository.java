package refrigerator.back.comment.outbound.repository.jpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.domain.entity.CommentHeart;

import javax.persistence.EntityManager;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class CommentHeartJpaRepository {

    private final EntityManager em;

    /**
     * 댓글 하트 저장 쿼리
     * @param commentHeart
     * @return 댓글 하트
     */
    public CommentHeart save(CommentHeart commentHeart){
        em.persist(commentHeart);
        return commentHeart;
    }

    /**
     * 댓글 하트 조회 쿼리
     * @param id
     * @return 댓글 하트
     */
    public Optional<CommentHeart> findById(Long id){
        return Optional.ofNullable(em.find(CommentHeart.class, id));
    }

}
