package refrigerator.back.ingredient.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;
import refrigerator.back.ingredient.outbound.mapper.OutSubIngredientMapper;

@Getter
@Builder
public class OutMyIngredientDTO {

    private Long id;
    private String name;
    private String unit;
    private Double volume;

    @QueryProjection
    public OutMyIngredientDTO(Long id, String name, String unit, Double volume) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.volume = volume;
    }

    public MyIngredientDTO mapping(OutSubIngredientMapper outSubIngredientMapper){
        return outSubIngredientMapper.toMyIngredientDTO(this);
    }
}
