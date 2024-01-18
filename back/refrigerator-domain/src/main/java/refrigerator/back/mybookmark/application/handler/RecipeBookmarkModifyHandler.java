package refrigerator.back.mybookmark.application.handler;

public interface RecipeBookmarkModifyHandler {
    void added(Long recipeId);
    void deleted(Long recipeId);
}
