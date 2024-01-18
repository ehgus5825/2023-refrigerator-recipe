package refrigerator.back.comment.application.port.batch;

import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;

import java.util.List;

public interface DeleteCommentBatchPort {

    Long deleteCommentHeart();

    Long deleteComment();

    void deleteCommentHeartPeople(CommentHeartPeople commentHeartPeople);
}