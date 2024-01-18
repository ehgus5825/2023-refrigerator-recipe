package refrigerator.back.ingredient.application.port.out;


import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;

import java.util.List;

public interface FindRegisteredIngredientPort {
    List<RegisteredIngredient> findIngredientList();

    RegisteredIngredient findIngredient(String name);
}
