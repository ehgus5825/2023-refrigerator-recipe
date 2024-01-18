package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.domain.value.IngredientSearchCondition;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;

import java.time.LocalDate;
import java.util.List;

public interface FindIngredientPort {

    IngredientDetailDTO getIngredientDetail(Long id);

    List<IngredientDTO> getIngredientList(LocalDate now, IngredientSearchCondition condition, int page, int size);

    List<IngredientDTO> getIngredientListOfAll(String email);

    List<IngredientDTO> getIngredientListByDeadline(LocalDate now, Long days, String email);

}
