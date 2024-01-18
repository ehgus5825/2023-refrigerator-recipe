package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.application.port.out.UpdateVolumeIngredientPort;
import refrigerator.back.ingredient.exception.IngredientExceptionType;
import refrigerator.back.ingredient.outbound.repository.jpa.IngredientPersistenceRepository;
import refrigerator.back.ingredient.outbound.repository.query.IngredientUpdateQueryRepository;
import refrigerator.back.ingredient.application.domain.entity.Ingredient;
import refrigerator.back.ingredient.application.port.out.DeleteIngredientPort;
import refrigerator.back.ingredient.application.port.out.SaveIngredientPort;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class IngredientUpdateAdapter implements DeleteIngredientPort, SaveIngredientPort, UpdateVolumeIngredientPort {

    private final IngredientPersistenceRepository ingredientRepository;
    private final IngredientUpdateQueryRepository ingredientUpdateQueryRepository;

    @Override
    public Long saveIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return ingredient.getId();
    }

    @Override
    public void deleteIngredient(Long id) {
        ingredientUpdateQueryRepository.updateIngredientDeleteStateTrue(id)
                .throwExceptionWhenNotAllowDuplicationResource(IngredientExceptionType.FAILED_TO_DELETE_INGREDIENTS);
    }

    @Override
    public void deleteAllIngredients(List<Long> ids) {
        ingredientUpdateQueryRepository.updateAllIngredientDeleteStateTrue(ids)
                .throwExceptionWhenAllowDuplicationResource(IngredientExceptionType.FAILED_TO_DELETE_INGREDIENTS);
    }

    @Override
    public void updateVolume(Long id, Double volume) {
        ingredientUpdateQueryRepository.updateVolumeIngredient(id, volume)
                .throwExceptionWhenNotAllowDuplicationResource(IngredientExceptionType.CAPACITY_MODIFICATION_FAILED);
    }
}
