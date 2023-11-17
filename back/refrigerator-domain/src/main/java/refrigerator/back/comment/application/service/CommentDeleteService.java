package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.port.in.DeleteCommentUseCase;
import refrigerator.back.comment.application.port.out.DeleteCommentPort;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentDeleteService implements DeleteCommentUseCase {

    private final DeleteCommentPort deleteCommentPort;

    @Override
    public void delete(Long commentId) {
        deleteCommentPort.delete(commentId);
    }
}
