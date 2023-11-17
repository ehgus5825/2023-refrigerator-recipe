package refrigerator.back.ingredient.application.port.in.ingredient.lookUp;

import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;

import java.time.LocalDate;
import java.util.List;

public interface FindIngredientListUseCase {
    List<IngredientDTO> getIngredientList(IngredientSearchCondition condition, int page, int size);

    List<IngredientDTO> getIngredientListOfAll(String email);

    List<IngredientDTO> getIngredientListByDeadline(Long days, String email);
}
