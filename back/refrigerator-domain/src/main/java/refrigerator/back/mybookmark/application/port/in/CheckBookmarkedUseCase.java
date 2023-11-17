package refrigerator.back.mybookmark.application.port.in;

public interface CheckBookmarkedUseCase {
    Boolean isBookmarked(Long recipeId, String memberId);
}
