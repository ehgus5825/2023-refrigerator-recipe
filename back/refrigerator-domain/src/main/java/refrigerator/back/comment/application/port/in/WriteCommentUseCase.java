package refrigerator.back.comment.application.port.in;

public interface WriteCommentUseCase {
    Long write(Long recipeId, String memberId, String content);
}
