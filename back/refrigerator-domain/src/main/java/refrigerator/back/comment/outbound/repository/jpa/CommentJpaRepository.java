package refrigerator.back.comment.outbound.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.comment.application.domain.entity.Comment;

public interface CommentJpaRepository extends JpaRepository<Comment, Long>{
}
