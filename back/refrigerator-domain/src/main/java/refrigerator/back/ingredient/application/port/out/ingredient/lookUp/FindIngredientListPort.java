package refrigerator.back.ingredient.application.port.out.ingredient.lookUp;

import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;

import java.time.LocalDate;
import java.util.List;

public interface FindIngredientListPort {

    List<Ingredient> getIngredients(String email);

    List<IngredientDTO> getIngredientList(LocalDate now, IngredientSearchCondition condition, int page, int size);

    List<IngredientDTO> getIngredientListOfAll(String email);

    List<IngredientDTO> getIngredientListByDeadline(LocalDate now, Long days, String email);

}
