package refrigerator.back.ingredient.application.port.batch;

public interface DeleteIngredientBatchPort {

    void deleteIngredients();

    Long deleteSuggestedIngredient();

}
