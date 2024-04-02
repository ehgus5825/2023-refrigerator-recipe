package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;
import refrigerator.back.comment.application.dto.CommentHeartPeopleDto;

import java.util.Map;

public interface FindCommentHeartPeoplePort {
    Map<Long, CommentHeartPeopleDto> findPeopleMap(String memberId);

    CommentHeartPeople findByCommentIdAndMemberId(Long commentId, String memberId);
}
