package refrigerator.back.recipe_recommend.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;
import refrigerator.back.recipe_recommend.application.port.out.FindMyIngredientsPort;
import refrigerator.back.recipe_recommend.outbound.mapper.OutRecommendRecipeDtoMapper;
import refrigerator.back.recipe_recommend.outbound.repository.RecipeRecommendSelectQueryRepository;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecommendMyIngredientLookUpAdapter implements FindMyIngredientsPort {

    private final RecipeRecommendSelectQueryRepository queryRepository;
    private final OutRecommendRecipeDtoMapper mapper;

    @Override
    public Set<MyIngredientDto> findMyIngredients(LocalDate startDate, String memberId) {
        return queryRepository.selectMyIngredientNames(startDate, memberId).stream()
                .map(myIngredient -> myIngredient.mapping(mapper))
                .collect(Collectors.toSet());
    }
}
