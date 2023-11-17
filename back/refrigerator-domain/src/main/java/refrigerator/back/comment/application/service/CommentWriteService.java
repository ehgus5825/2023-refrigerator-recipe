package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.Comment;
import refrigerator.back.comment.application.domain.CommentHeart;
import refrigerator.back.comment.application.port.in.WriteCommentUseCase;
import refrigerator.back.comment.application.port.out.SaveCommentPort;
import refrigerator.back.global.time.CurrentTime;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentWriteService implements WriteCommentUseCase {

    private final SaveCommentPort saveCommentPort;
    private final CurrentTime<LocalDateTime> currentTime;

    @Override
    public Long write(Long recipeId, String memberId, String content) {
        Comment comment = Comment.write(recipeId, memberId, content, currentTime.now());
        Long commentId = saveCommentPort.saveComment(comment);
        CommentHeart commentHeart = CommentHeart.create(commentId);
        saveCommentPort.saveCommentHeart(commentHeart);
        return commentId;
    }

}
