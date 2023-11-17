package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.port.in.ingredient.deducation.DeductionIngredientVolumeUseCase;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

// TODO : 벌크 연산으로 바꿔야 할지도 모르겠음

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientDeductionService implements DeductionIngredientVolumeUseCase {

    private final FindIngredientListPort findIngredientListPort;
    private final CurrentTime<LocalDate> currentTime;

    @Override
    public void deduction(String memberId, List<IngredientDeductionDTO> ingredientDTOList) {

        List<Ingredient> ingredientList = findIngredientListPort.getIngredients(memberId);

        IngredientsEmptyCheck(ingredientList);

        Map<String, Double> ingredientDTOMap = toIngredientMap(ingredientDTOList);

        List<Ingredient> availableIngredients = extractAvailableIngredients(currentTime.now(), ingredientList);

        dtoContainCheckBySafeIngredient(ingredientDTOMap,
                availableIngredients.stream().map(Ingredient::getName).collect(Collectors.toList()));

        availableIngredients.forEach(ingredient -> {
            Double volume = ingredientDTOMap.get(ingredient.getName());
            if(volume != null)
                ingredient.deductionVolume(volume);
        });
    }

    public void IngredientsEmptyCheck(List<Ingredient> ingredientList) {
        if (ingredientList.size() == 0)
            throw new BusinessException(EMPTY_INGREDIENT_LIST);
    }

    public Map<String, Double> toIngredientMap(List<IngredientDeductionDTO> ingredientDeductionDTOS){
        return ingredientDeductionDTOS.stream().collect(Collectors
                .toMap(IngredientDeductionDTO::getName, IngredientDeductionDTO::getVolume));
    }

    public List<Ingredient> extractAvailableIngredients(LocalDate now, List<Ingredient> ingredientList) {

        List<Ingredient> availableIngredients = ingredientList.stream()
                .filter(i -> ChronoUnit.DAYS.between(now, i.getExpirationDate()) >= 0)
                .collect(Collectors.toList());

        return availableIngredients;
    }

    public void dtoContainCheckBySafeIngredient(Map<String, Double> ingredientDTOMap, List<String> availableIngredientsNameList) {
        for (String name : ingredientDTOMap.keySet()) {
            if(!availableIngredientsNameList.contains(name))
                throw new BusinessException(EXCEEDED_EXPIRATION_DATE);
        }
    }
}
