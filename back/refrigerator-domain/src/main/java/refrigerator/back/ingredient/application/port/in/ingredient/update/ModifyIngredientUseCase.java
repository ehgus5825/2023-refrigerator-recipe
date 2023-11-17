package refrigerator.back.ingredient.application.port.in.ingredient.update;


import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;

public interface ModifyIngredientUseCase {

    void modifyIngredient(Long id, LocalDate expirationDate,
                          Double capacity, IngredientStorageType storageMethod);

}
