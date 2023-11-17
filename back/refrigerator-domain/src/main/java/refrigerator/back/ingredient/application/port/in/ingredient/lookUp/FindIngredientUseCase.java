package refrigerator.back.ingredient.application.port.in.ingredient.lookUp;


import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;

public interface FindIngredientUseCase {
    IngredientDetailDTO getIngredient(Long id);
}
