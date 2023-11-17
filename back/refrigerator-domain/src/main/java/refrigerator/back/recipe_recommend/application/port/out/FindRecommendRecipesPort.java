package refrigerator.back.recipe_recommend.application.port.out;

import refrigerator.back.recipe_recommend.application.dto.RecommendRecipeDto;

import java.util.List;
import java.util.Map;

public interface FindRecommendRecipesPort {
    List<RecommendRecipeDto> findByPercentMap(Map<Long, Double> percentMap);
}
