package refrigerator.back.recipe_recommend.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.recipe_recommend.application.dto.RecommendRecipeDto;
import refrigerator.back.recipe_recommend.application.port.out.FindRecommendRecipesPort;
import refrigerator.back.recipe_recommend.outbound.mapper.OutRecommendRecipeDtoMapper;
import refrigerator.back.recipe_recommend.outbound.repository.query.RecipeRecommendSelectQueryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecommendRecipeLookUpAdapter implements FindRecommendRecipesPort {

    private final RecipeRecommendSelectQueryRepository queryRepository;
    private final OutRecommendRecipeDtoMapper mapper;

    @Override
    public List<RecommendRecipeDto> findByPercentMap(Map<Long, Double> percentMap) {
        List<Long> recipeIds = new ArrayList<>(percentMap.keySet());
        return queryRepository.selectRecipeInfoByIds(recipeIds).stream()
                .map(recipe -> recipe.mapping(mapper, percentMap.get(recipe.getRecipeId())))
                .collect(Collectors.toList());
    }
}
