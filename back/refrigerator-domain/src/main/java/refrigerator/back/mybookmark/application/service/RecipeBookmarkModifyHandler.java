package refrigerator.back.mybookmark.application.service;

public interface RecipeBookmarkModifyHandler {
    void added(Long recipeId);
    void deleted(Long recipeId);
}
