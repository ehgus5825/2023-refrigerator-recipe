package refrigerator.back.recipe.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.recipe.application.dto.RecipeDto;
import refrigerator.back.recipe.outbound.mapper.OutRecipeDtoMapper;

@Getter
@Builder
public class OutRecipeDto {
    Long recipeId;
    String recipeName;
    String recipeImageName;
    Double scoreAvg;
    String description;
    Integer cookingTime;
    Integer kcal;
    Integer servings;
    String difficulty;

    @QueryProjection
    public OutRecipeDto(Long recipeId, String recipeName, String recipeImageName, Double scoreAvg, String description, Integer cookingTime, Integer kcal, Integer servings, String difficulty) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeImageName = recipeImageName;
        this.scoreAvg = scoreAvg;
        this.description = description;
        this.cookingTime = cookingTime;
        this.kcal = kcal;
        this.servings = servings;
        this.difficulty = difficulty;
    }

    public RecipeDto mapping(OutRecipeDtoMapper mapper, ImageUrlConvert imageUrlConvert){
        return mapper.toRecipeDto(this, imageUrlConvert.getUrl(recipeImageName));
    }

}
