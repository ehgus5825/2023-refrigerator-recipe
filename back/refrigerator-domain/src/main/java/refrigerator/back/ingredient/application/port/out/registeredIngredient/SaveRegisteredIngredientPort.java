package refrigerator.back.ingredient.application.port.out.registeredIngredient;

import refrigerator.back.ingredient.application.domain.RegisteredIngredient;

public interface SaveRegisteredIngredientPort {

    Long saveRegisteredIngredient(RegisteredIngredient registeredIngredient);
}
