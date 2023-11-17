package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.outbound.repository.query.CommentDeleteQueryRepository;
import refrigerator.back.comment.application.port.out.DeleteCommentPort;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.comment.outbound.repository.query.CommentUpdateQueryRepository;
import refrigerator.back.global.exception.BusinessException;

@Repository
@RequiredArgsConstructor
public class CommentDeleteAdapter implements DeleteCommentPort {

    private final CommentUpdateQueryRepository queryRepository;

    @Override
    public void delete(Long id) {
        queryRepository.updateCommentDeletedStatusById(id)
                .throwExceptionWhenNotAllowDuplicationResource(CommentExceptionType.FAIL_DELETE_COMMENT);
        queryRepository.updateCommentHeartDeletedStatusById(id)
                .throwExceptionWhenAllowDuplicationResource(CommentExceptionType.FAIL_DELETE_COMMENT);
    }
}
