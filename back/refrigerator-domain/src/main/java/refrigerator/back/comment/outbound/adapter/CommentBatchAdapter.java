package refrigerator.back.comment.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.port.batch.DeleteCommentBatchPort;
import refrigerator.back.comment.outbound.repository.query.CommentBatchQueryRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentBatchAdapter implements DeleteCommentBatchPort {

    private final CommentBatchQueryRepository commentBatchQueryRepository;

    @Override
    @Transactional
    public Long deleteCommentHeart(List<Long> ids) {
        return commentBatchQueryRepository.deleteCommentHeart(ids);
    }
}
