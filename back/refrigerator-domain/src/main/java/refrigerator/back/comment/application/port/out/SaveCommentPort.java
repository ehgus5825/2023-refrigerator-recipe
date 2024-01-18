package refrigerator.back.comment.application.port.out;

import refrigerator.back.comment.application.domain.entity.Comment;
import refrigerator.back.comment.application.domain.entity.CommentHeart;

public interface SaveCommentPort {
    Long saveComment(Comment comment);
    void saveCommentHeart(CommentHeart commentHeart);
}
