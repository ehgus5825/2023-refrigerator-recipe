package refrigerator.back.recipe_recommend.application.port.in;


import refrigerator.back.recipe_recommend.application.dto.RecommendRecipeDto;

import java.util.List;

public interface RecommendRecipeUseCase {
    List<RecommendRecipeDto> recommend(String memberId);
}
