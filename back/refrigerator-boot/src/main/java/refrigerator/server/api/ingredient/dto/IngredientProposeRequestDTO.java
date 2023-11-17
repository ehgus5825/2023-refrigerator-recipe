package refrigerator.server.api.ingredient.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IngredientProposeRequestDTO {

    @NotBlank
    String name;

    @NotBlank
    String unit;

}
