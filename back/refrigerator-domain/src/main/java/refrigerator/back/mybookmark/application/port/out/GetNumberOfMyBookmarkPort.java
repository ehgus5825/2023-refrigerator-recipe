package refrigerator.back.mybookmark.application.port.out;

public interface GetNumberOfMyBookmarkPort {
    Integer getByRecipeIdAndMemberId(Long recipeId, String memberId);
    Integer getByMemberId(String memberId);
}
