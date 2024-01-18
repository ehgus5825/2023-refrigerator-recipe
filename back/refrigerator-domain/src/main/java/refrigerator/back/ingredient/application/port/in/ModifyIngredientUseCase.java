package refrigerator.back.ingredient.application.port.in;


import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;

import java.time.LocalDate;

public interface ModifyIngredientUseCase {

    void modifyIngredient(Long id, LocalDate expirationDate,
                          Double capacity, IngredientStorageType storageMethod);

}
