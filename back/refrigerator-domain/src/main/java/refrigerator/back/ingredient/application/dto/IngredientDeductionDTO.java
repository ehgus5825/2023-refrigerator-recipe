package refrigerator.back.ingredient.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDeductionDTO {

    private String name;
    private Double volume;
    private String unit;
}
