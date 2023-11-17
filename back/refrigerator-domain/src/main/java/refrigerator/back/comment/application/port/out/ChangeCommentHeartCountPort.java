package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.CommentHeartValue;

public interface ChangeCommentHeartCountPort {
    void change(Long commentId, CommentHeartValue value);
}
