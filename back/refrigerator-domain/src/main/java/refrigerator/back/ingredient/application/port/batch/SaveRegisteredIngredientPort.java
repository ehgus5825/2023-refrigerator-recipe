package refrigerator.back.ingredient.application.port.batch;

import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;

public interface SaveRegisteredIngredientPort {

    Long saveRegisteredIngredient(RegisteredIngredient registeredIngredient);
}
