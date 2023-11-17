package refrigerator.back.recipe_recommend.application.domain;

import lombok.RequiredArgsConstructor;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;

import java.util.Map;

@RequiredArgsConstructor
public class RecommendRecipeIngredientMap {

    private final Map<String, RecommendRecipeIngredient> recipeIngredientMap;

    /* 레시피 식재료 가중치 전체 합 */
    public int getIngredientTypeWeightAmount(){
        return recipeIngredientMap.values().stream()
                .mapToInt(RecommendRecipeIngredient::getIngredientTypeWeight).sum();
    }

    /* 매칭된 식재료의 가중치 */
    public int getMatchedIngredientTypeWeight(MyIngredientDto myIngredient){
        RecommendRecipeIngredient recipeIngredientDto = recipeIngredientMap.get(myIngredient.getName());
        if (recipeIngredientDto != null){
            return recipeIngredientDto.getMatchedIngredientTypeWeight(myIngredient);
        }
        return 0;
    }

}
