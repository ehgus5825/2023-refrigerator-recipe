package refrigerator.back.recipe_recommend.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import refrigerator.back.recipe_recommend.application.domain.RecommendRecipeIngredient;
import refrigerator.back.recipe_recommend.outbound.mapper.OutRecommendRecipeDtoMapper;

@Getter
@ToString
public class OutRecipeIngredientDto {

    private Long recipeId;
    private String name;
    private String typeName;
    private Double volume;
    private String unit;

    @QueryProjection
    public OutRecipeIngredientDto(Long recipeId, String name, String typeName, Double volume, String unit) {
        this.recipeId = recipeId;
        this.name = name;
        this.typeName = typeName;
        this.volume = volume;
        this.unit = unit;
    }

    public RecommendRecipeIngredient mapping(OutRecommendRecipeDtoMapper mapper){
        return mapper.toRecipeIngredientDto(this);
    }
}
