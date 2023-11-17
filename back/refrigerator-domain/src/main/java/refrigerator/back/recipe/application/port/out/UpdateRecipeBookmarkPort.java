package refrigerator.back.recipe.application.port.out;

public interface UpdateRecipeBookmarkPort {
    void add(Long recipeId);
    void delete(Long recipeId);
}
