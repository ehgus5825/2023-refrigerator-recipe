package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.value.CommentHeartValue;

public interface ChangeCommentHeartCountPort {
    void change(Long commentId, CommentHeartValue value);
}
