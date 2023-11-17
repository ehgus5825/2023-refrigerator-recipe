package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.outbound.repository.jpa.CommentHeartJpaRepository;
import refrigerator.back.comment.outbound.repository.jpa.CommentJpaRepository;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.port.out.SaveCommentPort;

@Repository
@RequiredArgsConstructor
public class CommentSaveAdapter implements SaveCommentPort {

    private final CommentJpaRepository commentRepository;
    private final CommentHeartJpaRepository commentHeartRepository;

    @Override
    public Long saveComment(Comment comment) {
        commentRepository.save(comment);
        return comment.getCommentId();
    }

    @Override
    public void saveCommentHeart(CommentHeart commentHeart) {
        commentHeartRepository.save(commentHeart);
    }
}
