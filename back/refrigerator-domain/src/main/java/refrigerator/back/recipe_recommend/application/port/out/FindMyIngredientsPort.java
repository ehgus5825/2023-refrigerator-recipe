package refrigerator.back.recipe_recommend.application.port.out;

import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;

import java.time.LocalDate;
import java.util.Set;

public interface FindMyIngredientsPort {
    Set<MyIngredientDto> findMyIngredients(LocalDate startDate, String memberId);
}
