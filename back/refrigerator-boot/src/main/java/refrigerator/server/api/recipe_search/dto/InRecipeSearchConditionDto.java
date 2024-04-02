package refrigerator.server.api.recipe_search.dto;

import lombok.*;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InRecipeSearchConditionDto {

    private String recipeType;

    private String recipeFoodType;

    private String category;

    private String difficulty;

    private Double score;
}
