package refrigerator.back.recipe_recommend.application.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;
import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredientMap;

import java.util.*;

@RequiredArgsConstructor
public class RecommendRecipeMatchingService {

    private final Map<Long, RecommendRecipeIngredientMap> recipeIngredients;
    private final Set<MyIngredientDto> myIngredients;

    public Map<Long, Double> getRecipeMatchingPercent(){
        List<MatchingPercentDto> result = new ArrayList<>();
        for (Long recipeId : recipeIngredients.keySet()) {
            int matchAmount = getMatchAmount(recipeId);
            result.add(new MatchingPercentDto(recipeId, getMatchPercent(recipeId,  matchAmount)));
        }
        return filterRecipeMatchingPercent(result);
    }

    private Map<Long, Double> filterRecipeMatchingPercent(List<MatchingPercentDto> percents) {
        Map<Long, Double> percentMap = new HashMap<>();
        percents.stream()
                .sorted(Comparator.comparing(MatchingPercentDto::getMatchPercent).reversed())
                .filter(tuple -> tuple.getMatchPercent() > 0 && tuple.getMatchPercent() <= 100)
                .limit(11)
                .forEach(percent -> percentMap.put(percent.getRecipeId(), percent.getMatchPercent()));
        return percentMap;
    }

    private double getMatchPercent(Long recipeId, double matchAmount) {
        int recipeWeightAmount = recipeIngredients.get(recipeId).getIngredientTypeWeightAmount();
        return (matchAmount / recipeWeightAmount) * 100.0;
    }

    private int getMatchAmount(Long recipeId) {
        return myIngredients.stream()
                .mapToInt(myIngredient -> {
                    RecommendRecipeIngredientMap recipeIngredientMapDto = recipeIngredients.get(recipeId);
                    if (recipeIngredientMapDto == null){
                        return 0;
                    }
                    return recipeIngredientMapDto.getMatchedIngredientTypeWeight(myIngredient);
                })
                .sum();
    }

    @Getter
    @AllArgsConstructor
    private static class MatchingPercentDto {
        Long recipeId;
        Double matchPercent;
    }

}
