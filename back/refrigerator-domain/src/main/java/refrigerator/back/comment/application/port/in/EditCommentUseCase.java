package refrigerator.back.comment.application.port.in;

public interface EditCommentUseCase {
    void edit(String memberId, Long commentId, String content);
}
