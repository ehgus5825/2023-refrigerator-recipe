package refrigerator.back.ingredient.application.port.in;


import refrigerator.back.ingredient.application.dto.IngredientRegisterDTO;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;

import java.time.LocalDate;

public interface RegisterIngredientUseCase {

    IngredientRegisterDTO registerIngredient(String name, LocalDate expirationDate, Double capacity,
                                             String capacityUnit, IngredientStorageType storageMethod, Integer imageId, String email);
}
