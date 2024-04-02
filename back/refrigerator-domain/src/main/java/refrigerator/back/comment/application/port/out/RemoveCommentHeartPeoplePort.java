package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;

public interface RemoveCommentHeartPeoplePort {
    void remove(CommentHeartPeople commentHeartPeople);
}
