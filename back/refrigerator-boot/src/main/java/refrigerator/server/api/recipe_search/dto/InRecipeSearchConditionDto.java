package refrigerator.server.api.recipe_search.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeSearchConditionDto {

    @NotBlank
    private String recipeType;

    @NotBlank
    private String recipeFoodType;

    @NotBlank
    private String category;

    @NotBlank
    private String difficulty;

    @NotNull
    @PositiveOrZero
    private Double score;
}
