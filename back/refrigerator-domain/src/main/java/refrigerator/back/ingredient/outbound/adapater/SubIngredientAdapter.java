package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.domain.entity.SuggestedIngredient;
import refrigerator.back.ingredient.application.port.out.SaveSuggestedIngredientPort;
import refrigerator.back.ingredient.outbound.repository.query.SubIngredientQueryRepository;
import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.out.FindRegisteredIngredientPort;


import java.util.List;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@Component
@RequiredArgsConstructor
public class SubIngredientAdapter implements FindRegisteredIngredientPort, SaveSuggestedIngredientPort {

    private final SubIngredientQueryRepository subIngredientQueryRepository;

    @Override
    public List<RegisteredIngredient> findIngredientList() {
        if(subIngredientQueryRepository.getRegisteredIngredientList().isEmpty()){
            throw new BusinessException(NO_PREVIOUSLY_REGISTERED_INGREDIENTS);
        }

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
    public Long proposeIngredient(SuggestedIngredient ingredient) {
        return subIngredientQueryRepository.saveSuggestIngredient(ingredient);
    }
}
