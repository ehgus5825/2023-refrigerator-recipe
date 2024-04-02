package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;
import refrigerator.back.comment.application.dto.CommentHeartPeopleDto;
import refrigerator.back.comment.application.port.out.CheckExistCommentHeartPeoplePort;
import refrigerator.back.comment.application.port.out.FindCommentHeartPeoplePort;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.comment.outbound.mapper.OutCommentHeartPeopleMappingCollection;
import refrigerator.back.comment.outbound.repository.redis.CommentHeartPeopleRedisRepository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

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
    public Boolean checkByCommentIdAndMemberIdExist(Long commentId, String memberId) {
        return redisRepository.findByCommentIdAndMemberId(commentId, memberId).isPresent();
    }

    @Override
    public CommentHeartPeople findByCommentIdAndMemberId(Long commentId, String memberId) {
        return redisRepository.findByCommentIdAndMemberId(commentId, memberId)
                .orElseThrow(() -> new BusinessException(CommentExceptionType.FAIL_MODIFY_COMMENT));
    }

}
