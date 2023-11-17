package refrigerator.back.ingredient.application.port.batch;

public interface DeleteIngredientBatchPort {

    Long deleteIngredients();

    Long deleteSuggestedIngredient(String name);

}
