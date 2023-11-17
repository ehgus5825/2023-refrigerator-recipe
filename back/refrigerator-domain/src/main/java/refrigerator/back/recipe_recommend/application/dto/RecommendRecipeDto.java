package refrigerator.back.recipe_recommend.application.dto;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
public class RecommendRecipeDto {

    private Long recipeId;

    private String recipeName;

    private String recipeImage;

    private Double scoreAvg;

    private String percent;


}
