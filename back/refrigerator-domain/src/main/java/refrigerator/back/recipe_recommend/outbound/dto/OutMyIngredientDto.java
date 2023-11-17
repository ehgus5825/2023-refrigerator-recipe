package refrigerator.back.recipe_recommend.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;
import refrigerator.back.recipe_recommend.outbound.mapper.OutRecommendRecipeDtoMapper;

@Getter
@ToString
public class OutMyIngredientDto {

    private String name;
    private Double volume;
    private String unit;

    @QueryProjection
    public OutMyIngredientDto(String name, Double volume, String unit) {
        this.name = name;
        this.volume = volume;
        this.unit = unit;
    }

    public MyIngredientDto mapping(OutRecommendRecipeDtoMapper mapper){
        return mapper.toMyIngredientDto(this);
    }
}
