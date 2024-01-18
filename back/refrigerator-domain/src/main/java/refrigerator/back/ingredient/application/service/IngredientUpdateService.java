package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.entity.Ingredient;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.FindSubIngredientPort;
import refrigerator.back.ingredient.application.port.out.DeleteIngredientPort;
import refrigerator.back.ingredient.application.port.out.SaveIngredientPort;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientUpdateService implements ModifyIngredientUseCase, RemoveIngredientUseCase {

    private final FindSubIngredientPort findSubIngredientPort;
    private final DeleteIngredientPort deleteIngredientPort;
    private final SaveIngredientPort saveIngredientPort;

    @Override
    public void modifyIngredient(Long id, LocalDate expirationDate, Double capacity, IngredientStorageType storageMethod) {
        Ingredient ingredient = findSubIngredientPort.getIngredient(id);
        ingredient.modify(expirationDate, capacity, storageMethod);
        saveIngredientPort.saveIngredient(ingredient);
    }

    @Override
    public void removeIngredient(Long id) {
        deleteIngredientPort.deleteIngredient(id);
    }

    @Override
    public void removeAllIngredients(List<Long> ids) {
        deleteIngredientPort.deleteAllIngredients(ids);
    }
}
