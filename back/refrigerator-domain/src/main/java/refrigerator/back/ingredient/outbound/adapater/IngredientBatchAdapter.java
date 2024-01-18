package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.batch.SaveRegisteredIngredientPort;
import refrigerator.back.ingredient.outbound.repository.query.IngredientBatchQueryRepository;
import refrigerator.back.ingredient.application.port.batch.DeleteIngredientBatchPort;

@Slf4j
@Component
@RequiredArgsConstructor
public class IngredientBatchAdapter implements DeleteIngredientBatchPort, SaveRegisteredIngredientPort {

    private final IngredientBatchQueryRepository ingredientBatchQueryRepository;

    @Override
    @Transactional
    public void deleteIngredients() {
        ingredientBatchQueryRepository.deleteIngredients();
    }

    @Override
    @Transactional
    public Long deleteSuggestedIngredient() {
        return ingredientBatchQueryRepository.deleteSuggestedIngredient();
    }

    @Override
    @Transactional
    public Long saveRegisteredIngredient(RegisteredIngredient registeredIngredient) {
        return ingredientBatchQueryRepository.saveRegisteredIngredient(registeredIngredient);
    }
}
