package refrigerator.back.comment.application.port.batch;

import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;

import java.util.List;

public interface FindCommentHeartPeopleForDeletePort {

    List<CommentHeartPeople> findByCommentID(Long commentId);
}