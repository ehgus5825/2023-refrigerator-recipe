package refrigerator.back.recipe_recommend.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredientMap;
import refrigerator.back.recipe_recommend.application.port.out.FindRecipeIngredientMapPort;
import refrigerator.back.recipe_recommend.outbound.mapper.OutRecipeIngredientDtoMappingCollection;
import refrigerator.back.recipe_recommend.outbound.mapper.OutRecommendRecipeDtoMapper;
import refrigerator.back.recipe_recommend.outbound.repository.RecipeRecommendSelectQueryRepository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RecommendRecipeIngredientLookUpAdapter implements FindRecipeIngredientMapPort {

    private final RecipeRecommendSelectQueryRepository queryRepository;
    private final OutRecommendRecipeDtoMapper mapper;

    @Override
    public Map<Long, RecommendRecipeIngredientMap> findMap() {
        OutRecipeIngredientDtoMappingCollection collection = new OutRecipeIngredientDtoMappingCollection(queryRepository.selectRecipeIngredientDtoList());
        return collection.getRecipeIngredientMap(mapper);
    }

}
