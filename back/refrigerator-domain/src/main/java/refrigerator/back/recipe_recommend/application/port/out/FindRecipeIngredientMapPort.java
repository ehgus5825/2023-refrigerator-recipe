package refrigerator.back.recipe_recommend.application.port.out;

import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredientMap;

import java.util.Map;

public interface FindRecipeIngredientMapPort {
    Map<Long, RecommendRecipeIngredientMap> findMap();
}
