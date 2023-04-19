package refrigerator.back.comment.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.adapter.out.repository.CommentRepository;
import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.port.out.CommentHeartPeopleReadPort;
import refrigerator.back.comment.application.port.out.CommentHeartPeopleWritePort;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.exception.BusinessException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentHeartPeoplePersistenceAdapter implements CommentHeartPeopleReadPort, CommentHeartPeopleWritePort {

    private final CommentRepository commentRepository;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Long> findLikedComment(String memberId) {
        return commentRepository.findLikedListByMemberId(memberId);
    }

    @Override
    public Long addHeartPeople(CommentHeartPeople commentHeartPeople) {
        String result = stringRedisTemplate.opsForValue().get(commentHeartPeople.getSubPeopleId());
        if (result != null){
            throw new BusinessException(CommentExceptionType.DUPLICATE_HEART_REQUEST);
        }
        Long peopleId = commentRepository.persistCommentHeartPeople(commentHeartPeople);
        stringRedisTemplate.opsForValue().set(
                commentHeartPeople.getSubPeopleId(),
                Long.toString(peopleId));
        return peopleId;
    }

    @Override
    public void removeHeartPeople(String memberId, Long commentId) {
        String peopleKey = CommentHeartPeople.makeSubPeopleId(memberId, commentId);
        String result = stringRedisTemplate.opsForValue()
                .get(peopleKey);
        if (result == null){
            throw new BusinessException(CommentExceptionType.DUPLICATE_HEART_REQUEST);
        }
        commentRepository.deleteCommentHeartPeople(memberId, commentId);
        stringRedisTemplate.delete(peopleKey);
    }
}