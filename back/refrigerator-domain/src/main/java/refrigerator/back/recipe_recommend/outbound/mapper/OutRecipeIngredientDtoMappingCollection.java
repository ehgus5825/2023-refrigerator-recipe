package refrigerator.back.recipe_recommend.outbound.mapper;

import lombok.RequiredArgsConstructor;
import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredient;
import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredientMap;
import refrigerator.back.recipe_recommend.outbound.dto.OutRecipeIngredientDto;

import java.util.*;

@RequiredArgsConstructor
public class OutRecipeIngredientDtoMappingCollection {

    private final List<OutRecipeIngredientDto> recipeIngredients;

    public Map<Long, RecommendRecipeIngredientMap> getRecipeIngredientMap(OutRecommendRecipeDtoMapper mapper){
        Map<Long, Map<String, RecommendRecipeIngredient>> map = new HashMap<>();
        recipeIngredients.forEach(recipeIngredient -> {
            Long recipeId = recipeIngredient.getRecipeId();
            Map<String, RecommendRecipeIngredient> value = map.getOrDefault(recipeId, new HashMap<>());
            value.put(recipeIngredient.getName(), recipeIngredient.mapping(mapper));
            map.put(recipeId, value);
        });
        return getResultMap(map);
    }

    private Map<Long, RecommendRecipeIngredientMap> getResultMap(Map<Long, Map<String, RecommendRecipeIngredient>> map) {
        Map<Long, RecommendRecipeIngredientMap> result = new HashMap<>();
        map.keySet().forEach(key -> result.put(key, new RecommendRecipeIngredientMap(map.get(key))));
        return result;
    }
}
