package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;
import refrigerator.back.comment.application.port.out.RemoveCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.out.SaveCommentHeartPeoplePort;
import refrigerator.back.comment.outbound.repository.redis.CommentHeartPeopleRedisRepository;

/**
 * CommentHeartPeople 를 저장/삭제를 진행하는 어뎁터
 */
@Repository
@RequiredArgsConstructor
public class CommentHeartPeopleWriteAdapter implements SaveCommentHeartPeoplePort, RemoveCommentHeartPeoplePort {

    private final CommentHeartPeopleRedisRepository redisRepository;

    @Override
    public void remove(CommentHeartPeople commentHeartPeople) {
        redisRepository.delete(commentHeartPeople);
    }

    @Override
    public void save(CommentHeartPeople commentHeartPeople){
        redisRepository.save(commentHeartPeople);
    }

}
