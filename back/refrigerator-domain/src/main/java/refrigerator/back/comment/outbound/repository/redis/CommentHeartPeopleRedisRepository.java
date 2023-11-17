package refrigerator.back.comment.outbound.repository.redis;

import org.springframework.data.repository.CrudRepository;
import refrigerator.back.comment.application.domain.CommentHeartPeople;

import java.util.List;
import java.util.Optional;

public interface CommentHeartPeopleRedisRepository extends CrudRepository<CommentHeartPeople, String> {
    Optional<CommentHeartPeople> findByCommentIdAndMemberId(Long commentId, String memberId);
    List<CommentHeartPeople> findByMemberId(String memberId);
}
