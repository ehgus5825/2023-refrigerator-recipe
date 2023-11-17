package refrigerator.back.recipe_search.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.recipe_search.application.dto.RecipeSearchDto;
import refrigerator.back.recipe_search.outbound.mapper.OutRecipeSearchDtoMapper;

import java.io.Serializable;

@Getter
@Builder
@EqualsAndHashCode
public class OutRecipeSearchDto implements Serializable {
    private Long recipeId;
    private String recipeName;
    private String recipeImageName;
    private Double scoreAvg;
    private Integer views;

    @QueryProjection
    public OutRecipeSearchDto(Long recipeId, String recipeName, String recipeImageName, Double scoreAvg, Integer views) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeImageName = recipeImageName;
        this.scoreAvg = scoreAvg;
        this.views = views;
    }

    public RecipeSearchDto mapping(OutRecipeSearchDtoMapper mapper, ImageUrlConvert imageUrlConvert){
        return mapper.toInRecipeSearchDto(this, imageUrlConvert.getUrl(recipeImageName));
    }
}
