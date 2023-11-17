package refrigerator.back.ingredient.application.port.out.ingredient.update;

import java.util.List;

public interface DeleteIngredientPort {

    Long deleteIngredient(Long id);

    Long deleteAllIngredients(List<Long> ids);
}
