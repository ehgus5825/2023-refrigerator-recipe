package refrigerator.back.ingredient.application.port.out.ingredient.update;

import refrigerator.back.ingredient.application.domain.Ingredient;

public interface SaveIngredientPort {

    Long saveIngredient(Ingredient ingredient);

}
