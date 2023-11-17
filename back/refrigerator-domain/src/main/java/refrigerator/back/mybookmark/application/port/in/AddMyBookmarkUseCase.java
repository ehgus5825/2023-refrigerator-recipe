package refrigerator.back.mybookmark.application.port.in;

public interface AddMyBookmarkUseCase {
    Long add(String memberId, Long recipeId);
}
