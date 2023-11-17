package refrigerator.back.recipe_recommend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchingPercentDto {
    private Long recipeId;
    private Double matchPercent;
}
