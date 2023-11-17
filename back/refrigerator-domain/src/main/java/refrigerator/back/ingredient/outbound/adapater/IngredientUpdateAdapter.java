package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.outbound.repository.IngredientPersistenceRepository;
import refrigerator.back.ingredient.outbound.repository.IngredientUpdateQueryRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.port.out.ingredient.update.DeleteIngredientPort;
import refrigerator.back.ingredient.application.port.out.ingredient.update.SaveIngredientPort;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class IngredientUpdateAdapter implements DeleteIngredientPort, SaveIngredientPort {

    private final IngredientPersistenceRepository ingredientRepository;
    private final IngredientUpdateQueryRepository ingredientUpdateQueryRepository;

    @Override
    public Long saveIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return ingredient.getId();
    }

    @Override
    public Long deleteIngredient(Long id) {
        return ingredientUpdateQueryRepository.updateIngredientDeleteStateTrue(id);
    }

    @Override
    public Long deleteAllIngredients(List<Long> ids) {
        return ingredientUpdateQueryRepository.updateAllIngredientDeleteStateTrue(ids);
    }

}
