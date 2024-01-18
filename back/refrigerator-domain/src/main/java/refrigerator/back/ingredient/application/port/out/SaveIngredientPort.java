package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.application.domain.entity.Ingredient;

public interface SaveIngredientPort {

    Long saveIngredient(Ingredient ingredient);

}
