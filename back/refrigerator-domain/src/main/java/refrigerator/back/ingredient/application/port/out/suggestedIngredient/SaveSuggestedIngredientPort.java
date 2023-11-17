package refrigerator.back.ingredient.application.port.out.suggestedIngredient;

import refrigerator.back.ingredient.application.domain.SuggestedIngredient;

public interface SaveSuggestedIngredientPort {

    Long proposeIngredient(SuggestedIngredient ingredient);
}
