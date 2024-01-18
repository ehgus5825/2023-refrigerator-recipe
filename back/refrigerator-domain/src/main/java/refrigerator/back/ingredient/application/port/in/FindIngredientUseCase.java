package refrigerator.back.ingredient.application.port.in;

import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.domain.value.IngredientSearchCondition;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;

import java.util.List;

public interface FindIngredientUseCase {
    List<IngredientDTO> getIngredientList(IngredientSearchCondition condition, int page, int size);

    List<IngredientDTO> getIngredientListOfAll(String email);

    List<IngredientDTO> getIngredientListByDeadline(Long days, String email);

    IngredientDetailDTO getIngredient(Long id);
}
