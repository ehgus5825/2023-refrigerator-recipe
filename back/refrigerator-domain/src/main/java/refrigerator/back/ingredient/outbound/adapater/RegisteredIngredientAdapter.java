package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.port.out.registeredIngredient.SaveRegisteredIngredientPort;
import refrigerator.back.ingredient.outbound.repository.SubIngredientQueryRepository;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.out.registeredIngredient.FindRegisteredIngredientPort;
import refrigerator.back.ingredient.exception.IngredientExceptionType;


import java.util.List;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@Component
@RequiredArgsConstructor
public class RegisteredIngredientAdapter implements FindRegisteredIngredientPort, SaveRegisteredIngredientPort {

    private final SubIngredientQueryRepository subIngredientQueryRepository;

    @Override
    public List<RegisteredIngredient> findIngredientList() {
        return subIngredientQueryRepository.getRegisteredIngredientList();
    }

    @Override
    public RegisteredIngredient findIngredient(String name) {

        return subIngredientQueryRepository.getRegisteredIngredientList().stream()
                .filter(ingredient -> ingredient.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new BusinessException(NOT_A_PREVIOUSLY_REGISTERED_INGREDIENT));
    }

    @Override
    @Transactional
    public Long saveRegisteredIngredient(RegisteredIngredient registeredIngredient) {
        return subIngredientQueryRepository.saveRegisteredIngredient(registeredIngredient);
    }
}
