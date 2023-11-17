package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.comment.outbound.repository.query.CommentUpdateQueryRepository;
import refrigerator.back.comment.application.port.out.ModifyCommentPort;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class CommentModifyAdapter implements ModifyCommentPort {

    private final CommentUpdateQueryRepository updateQueryRepository;

    @Override
    public void modifyContent(Long id, String content, LocalDateTime now) {
        updateQueryRepository.updateCommentToContent(id, content, now)
                .throwExceptionWhenNotAllowDuplicationResource(CommentExceptionType.FAIL_MODIFY_COMMENT);
    }
}
