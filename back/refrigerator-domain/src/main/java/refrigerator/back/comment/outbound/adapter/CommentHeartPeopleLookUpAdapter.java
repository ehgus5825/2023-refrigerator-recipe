package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.dto.CommentHeartPeopleDto;
import refrigerator.back.comment.application.port.out.CheckExistCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.out.FindCommentHeartPeoplePort;
import refrigerator.back.comment.outbound.mapper.OutCommentHeartPeopleMappingCollection;
import refrigerator.back.comment.outbound.repository.redis.CommentHeartPeopleRedisRepository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CommentHeartPeopleLookUpAdapter implements FindCommentHeartPeoplePort, CheckExistCommentHeartPeoplePort {

    private final CommentHeartPeopleRedisRepository redisRepository;

    @Override
    public Map<Long, CommentHeartPeopleDto> findPeopleMap(String memberId) {
        OutCommentHeartPeopleMappingCollection collection = new OutCommentHeartPeopleMappingCollection(redisRepository.findByMemberId(memberId));
        return collection.getPeopleMap();
    }

    @Override
    public Boolean checkByCommentIdAndMemberId(Long commentId, String memberId) {
        return redisRepository.findByCommentIdAndMemberId(commentId, memberId).isPresent();
    }

    @Override
    public Boolean checkByPeopleId(String peopleId) {
        return redisRepository.findById(peopleId).isPresent();
    }

}
