package refrigerator.back.ingredient.application.port.out;

import refrigerator.back.ingredient.application.domain.entity.Ingredient;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;

import java.time.LocalDate;
import java.util.List;

public interface FindSubIngredientPort {

    Ingredient getIngredient(Long id);

    List<MyIngredientDTO> getAvailableIngredients(String email, LocalDate date);
}
