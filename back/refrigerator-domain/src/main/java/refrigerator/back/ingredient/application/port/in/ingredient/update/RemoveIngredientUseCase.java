package refrigerator.back.ingredient.application.port.in.ingredient.update;

import java.util.List;

public interface RemoveIngredientUseCase {
    Long removeIngredient(Long id);

    Long removeAllIngredients(List<Long> ids);
}
