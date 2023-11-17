package refrigerator.back.ingredient.application.port.out.registeredIngredient;


import refrigerator.back.ingredient.application.domain.RegisteredIngredient;

import java.util.List;

public interface FindRegisteredIngredientPort {
    List<RegisteredIngredient> findIngredientList();

    RegisteredIngredient findIngredient(String name);
}
