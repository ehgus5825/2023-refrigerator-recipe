package refrigerator.back.mybookmark.application.port.in;

public interface DeleteMyBookmarkUseCase {
    Long delete(Long recipeId, String memberId);
}
