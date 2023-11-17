package refrigerator.server.api.recipe.dto;

import lombok.*;
import refrigerator.back.recipe.application.dto.RecipeCourseDto;
import refrigerator.back.recipe.application.dto.RecipeDto;
import refrigerator.back.recipe.application.dto.RecipeIngredientDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InRecipeDto {
    RecipeDto recipeInfo;
    List<RecipeIngredientDto> ingredients;
    List<RecipeCourseDto> courses;
    Boolean isCooked;
    Boolean isBookmarked;
}
