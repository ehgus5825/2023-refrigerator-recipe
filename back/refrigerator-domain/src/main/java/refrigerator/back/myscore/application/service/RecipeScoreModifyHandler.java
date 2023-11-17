package refrigerator.back.myscore.application.service;

public interface RecipeScoreModifyHandler {
    void addUp(Long recipeId, Double newScore);
    void renew(Long recipeId, Double oldScore, Double newScore);
}
