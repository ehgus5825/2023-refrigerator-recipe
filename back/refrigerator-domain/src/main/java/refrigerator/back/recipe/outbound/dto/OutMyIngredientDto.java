package refrigerator.back.recipe.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import refrigerator.back.recipe.application.dto.MyIngredientDto;
import refrigerator.back.recipe.outbound.mapper.OutMyIngredientDtoMapper;

@Getter
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

    public MyIngredientDto mapping(OutMyIngredientDtoMapper mapper){
        return mapper.toMyIngredientDto(this);
    }
}
