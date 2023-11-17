package refrigerator.back.recipe_recommend.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.recipe_recommend.application.dto.RecommendRecipeDto;
import refrigerator.back.recipe_recommend.outbound.mapper.OutRecommendRecipeDtoMapper;

@Getter
@ToString
public class OutRecommendRecipeDto {

    private Long recipeId;
    private String recipeName;
    private String recipeImageName;
    private Double scoreAvg;

    @QueryProjection
    public OutRecommendRecipeDto(Long recipeId, String recipeName, String recipeImageName, Double scoreAvg) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeImageName = recipeImageName;
        this.scoreAvg = scoreAvg;
    }

    public RecommendRecipeDto mapping(OutRecommendRecipeDtoMapper mapper,
                                      ImageUrlConvert imageUrlConvert,
                                      Double percent){
        return mapper.toRecommendRecipeDto(
                this,
                imageUrlConvert.getUrl(recipeImageName),
                percent);
    }

}
