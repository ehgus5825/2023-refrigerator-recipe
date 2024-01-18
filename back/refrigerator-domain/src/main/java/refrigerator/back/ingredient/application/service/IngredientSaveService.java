package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.domain.entity.Ingredient;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientRegisterDTO;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.SaveIngredientPort;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientSaveService implements RegisterIngredientUseCase {

    private final SaveIngredientPort saveIngredientPort;
    private final CurrentTime<LocalDate> currentTime;

    @Override
    public IngredientRegisterDTO registerIngredient(String name, LocalDate expirationDate, Double capacity,
                                                    String capacityUnit, IngredientStorageType storageMethod, Integer image, String email) {

        return IngredientRegisterDTO.builder()
                .ingredientID(saveIngredientPort.saveIngredient(Ingredient.create(name, expirationDate, currentTime.now(), capacity,
                        capacityUnit, storageMethod, image, email))).build();
    }
}
