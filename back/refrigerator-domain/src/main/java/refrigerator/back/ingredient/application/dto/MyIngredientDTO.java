package refrigerator.back.ingredient.application.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyIngredientDTO {

    private Long id;
    private String name;
    private String unit;
    private Double volume;
}
