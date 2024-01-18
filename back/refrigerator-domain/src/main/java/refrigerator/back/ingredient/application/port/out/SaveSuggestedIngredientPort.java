package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.application.domain.entity.SuggestedIngredient;

public interface SaveSuggestedIngredientPort {

    Long proposeIngredient(SuggestedIngredient ingredient);
}
