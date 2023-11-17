package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.outbound.repository.SubIngredientQueryRepository;
import refrigerator.back.ingredient.application.domain.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.out.suggestedIngredient.SaveSuggestedIngredientPort;

@Component
@RequiredArgsConstructor
public class SuggestedIngredientAdapter implements SaveSuggestedIngredientPort {

    private final SubIngredientQueryRepository subIngredientQueryRepository;

    @Override
    public Long proposeIngredient(SuggestedIngredient ingredient) {
        return subIngredientQueryRepository.saveSuggestIngredient(ingredient);
    }
}
