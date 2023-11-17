package refrigerator.back.ingredient.application.port.in.registeredIngredient;


import refrigerator.back.ingredient.application.domain.RegisteredIngredient;

import java.util.List;

public interface FindRegisteredIngredientUseCase {

    List<RegisteredIngredient> getIngredientList();

    RegisteredIngredient getIngredient(String name);
}
