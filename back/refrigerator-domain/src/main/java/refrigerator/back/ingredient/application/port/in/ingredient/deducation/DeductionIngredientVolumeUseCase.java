package refrigerator.back.ingredient.application.port.in.ingredient.deducation;

import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;

import java.time.LocalDate;
import java.util.List;

public interface DeductionIngredientVolumeUseCase {
    void deduction(String memberId, List<IngredientDeductionDTO> ingredients);
}
