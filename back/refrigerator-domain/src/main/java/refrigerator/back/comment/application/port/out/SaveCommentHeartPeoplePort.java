package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;

public interface SaveCommentHeartPeoplePort {
    void save(CommentHeartPeople commentHeartPeople);
}
