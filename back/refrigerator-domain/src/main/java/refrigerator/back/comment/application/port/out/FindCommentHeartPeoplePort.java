package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.CommentHeartPeople;
import refrigerator.back.comment.application.dto.CommentHeartPeopleDto;

import java.util.Map;
import java.util.Optional;

public interface FindCommentHeartPeoplePort {
    Map<Long, CommentHeartPeopleDto> findPeopleMap(String memberId);
}
