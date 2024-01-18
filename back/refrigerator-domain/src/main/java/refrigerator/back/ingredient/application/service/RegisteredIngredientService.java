package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;
import refrigerator.back.ingredient.application.port.in.FindRegisteredIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.FindRegisteredIngredientPort;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegisteredIngredientService implements FindRegisteredIngredientUseCase {
    
    private final FindRegisteredIngredientPort findRegisteredIngredientPort;

    @Override
    public List<RegisteredIngredient> getIngredientList() {
        return findRegisteredIngredientPort.findIngredientList();
    }

    @Override
    public RegisteredIngredient getIngredient(String name) {
        return findRegisteredIngredientPort.findIngredient(name);
    }
}
