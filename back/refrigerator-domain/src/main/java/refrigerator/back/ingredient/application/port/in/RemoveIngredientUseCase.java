package refrigerator.back.ingredient.application.port.in;

import java.util.List;

public interface RemoveIngredientUseCase {
    void removeIngredient(Long id);

    void removeAllIngredients(List<Long> ids);
}
