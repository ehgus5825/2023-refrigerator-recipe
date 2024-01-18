package refrigerator.back.comment.application.port.in;

public interface EditCommentUseCase {

    void edit(Long commentId, String content);
}
