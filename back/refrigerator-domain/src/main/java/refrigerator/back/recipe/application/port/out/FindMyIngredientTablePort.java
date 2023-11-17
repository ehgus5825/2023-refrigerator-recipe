package refrigerator.back.recipe.application.port.out;


import refrigerator.back.recipe.application.dto.MyIngredientDto;

import java.util.Map;

public interface FindMyIngredientTablePort {
    Map<String, MyIngredientDto> findMyIngredients(String memberId);
}
