package refrigerator.back.ingredient.application.port.out;

import java.util.List;

public interface DeleteIngredientPort {

    void deleteIngredient(Long id);

    void deleteAllIngredients(List<Long> ids);
}
