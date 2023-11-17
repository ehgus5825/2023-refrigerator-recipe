package refrigerator.back.recipe.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import refrigerator.back.recipe.application.dto.RecipeIngredientDto;
import refrigerator.back.recipe.outbound.mapper.OutRecipeDtoMapper;

@Getter
@Builder
@EqualsAndHashCode
public class OutRecipeIngredientDto {

    private Long ingredientId;
    private String name;
    private String volume;
    private Double transVolume;
    private String transUnit;
    private String type;

    @QueryProjection
    public OutRecipeIngredientDto(Long ingredientId, String name, String volume, Double transVolume, String transUnit, String type) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.volume = volume;
        this.transVolume = transVolume;
        this.transUnit = transUnit;
        this.type = type;
    }

    public RecipeIngredientDto mapping(OutRecipeDtoMapper mapper){
        return mapper.toRecipeIngredientDto(this);
    }
}
