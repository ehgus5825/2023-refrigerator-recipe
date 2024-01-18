package refrigerator.back.ingredient.application.service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.domain.value.IngredientSearchCondition;
import refrigerator.back.ingredient.application.port.in.FindIngredientUseCase;
import refrigerator.back.ingredient.application.port.out.FindIngredientPort;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class IngredientLookUpService implements FindIngredientUseCase {

    private final FindIngredientPort findIngredientPort;
    private final CurrentTime<LocalDate> currentTime;
    
    @Override
    public List<IngredientDTO> getIngredientList(IngredientSearchCondition condition, int page, int size) {
        List<IngredientDTO> ingredients = findIngredientPort.getIngredientList(currentTime.now(), condition, page, size);
        calculateRemainDaysList(ingredients);
        return ingredients;
    }

    @Override
    public List<IngredientDTO> getIngredientListOfAll(String email) {
        List<IngredientDTO> ingredients = findIngredientPort.getIngredientListOfAll(email);
        calculateRemainDaysList(ingredients);
        return ingredients;
    }

    @Override
    public List<IngredientDTO> getIngredientListByDeadline(Long days, String email) {
        List<IngredientDTO> ingredients = findIngredientPort.getIngredientListByDeadline(currentTime.now(), days, email);
        calculateRemainDaysList(ingredients);
        return ingredients;
    }

    @Override
    public IngredientDetailDTO getIngredient(Long id) {
        IngredientDetailDTO ingredient = findIngredientPort.getIngredientDetail(id);
        ingredient.calculateRemainDays(currentTime.now());

        return ingredient;
    }

    public void calculateRemainDaysList(List<IngredientDTO> ingredientDTOS) {
        for (IngredientDTO ingredientDTO : ingredientDTOS) {
            ingredientDTO.calculateRemainDays(currentTime.now());
        }
    }
}
