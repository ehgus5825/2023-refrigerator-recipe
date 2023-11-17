package refrigerator.back.recipe_recommend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredientMap;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;
import refrigerator.back.recipe_recommend.application.dto.RecommendRecipeDto;
import refrigerator.back.recipe_recommend.application.port.in.RecommendRecipeUseCase;
import refrigerator.back.recipe_recommend.application.port.out.FindMyIngredientsPort;
import refrigerator.back.recipe_recommend.application.port.out.FindRecipeIngredientMapPort;
import refrigerator.back.recipe_recommend.application.port.out.FindRecommendRecipesPort;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeRecommendService implements RecommendRecipeUseCase {

    private final FindMyIngredientsPort findMyIngredientsPort;
    private final FindRecipeIngredientMapPort findRecipeIngredientMapPort;
    private final FindRecommendRecipesPort findRecommendRecipesPort;
    private final CurrentTime<LocalDate> currentTime;

    @Override
    public List<RecommendRecipeDto> recommend(String memberId) {
        Set<MyIngredientDto> myIngredients = findMyIngredientsPort.findMyIngredients(currentTime.now(), memberId);
        Map<Long, RecommendRecipeIngredientMap> recipeIngredientMap = findRecipeIngredientMapPort.findMap();
        RecommendRecipeMatchingService service = new RecommendRecipeMatchingService(recipeIngredientMap, myIngredients);
        return findRecommendRecipesPort.findByPercentMap(service.getRecipeMatchingPercent());
    }
}
