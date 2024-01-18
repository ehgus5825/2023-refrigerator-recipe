package refrigerator.back.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.comment.application.domain.entity.CommentHeartPeople;
import refrigerator.back.comment.application.port.batch.DeleteCommentBatchPort;
import refrigerator.back.comment.application.port.batch.DeleteCommentUseCase;
import refrigerator.back.comment.application.port.batch.FindCommentHeartPeopleForDeletePort;
import refrigerator.back.comment.application.port.batch.FindDeletedCommentPort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentBatchService implements DeleteCommentUseCase {

    private final DeleteCommentBatchPort deleteCommentBatchPort;
    private final FindDeletedCommentPort findDeletedCommentPort;
    private final FindCommentHeartPeopleForDeletePort findCommentHeartPeopleForDeletePort;

    @Override
    @Transactional
    public void deleteComment() {
        List<Long> ids = findDeletedCommentPort.findDeletedComment();
        for (Long commentId : ids) {
            List<CommentHeartPeople> commentHeartPeople = findCommentHeartPeopleForDeletePort.findByCommentID(commentId);
            for (CommentHeartPeople commentHeartPerson : commentHeartPeople) {
                deleteCommentBatchPort.deleteCommentHeartPeople(commentHeartPerson);
            }
        }
        Long deletedCommentHeart = deleteCommentBatchPort.deleteCommentHeart();
        Long deletedComment = deleteCommentBatchPort.deleteComment();
        if(!deletedComment.equals(deletedCommentHeart)){
            throw new RuntimeException();
        }
    }
}