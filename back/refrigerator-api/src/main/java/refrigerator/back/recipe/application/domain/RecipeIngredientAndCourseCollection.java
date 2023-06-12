package refrigerator.back.recipe.application.domain;

import refrigerator.back.recipe.adapter.in.dto.InRecipeCourseDto;
import refrigerator.back.recipe.adapter.in.dto.InRecipeIngredientDto;
import refrigerator.back.recipe.adapter.mapper.RecipeCourseDataMapper;
import refrigerator.back.recipe.adapter.mapper.RecipeIngredientDataMapper;
import refrigerator.back.recipe.application.domain.entity.RecipeCourse;
import refrigerator.back.recipe.application.domain.entity.RecipeIngredient;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeIngredientAndCourseCollection {

    private final Set<RecipeIngredient> ingredients;
    private final Set<RecipeCourse> courses;


    public RecipeIngredientAndCourseCollection(
            Set<RecipeIngredient> ingredients,
            Set<RecipeCourse> courses) {
        this.ingredients = ingredients;
        this.courses = courses;
    }

    /* 제대로 값이 있는지 확인하는 함수, 주로 테스트에서 사용 */
    public boolean isValid(){
        if (ingredients != null && courses != null){
            return ingredients.size() > 0 && courses.size() > 0;
        }
        return false;
    }

    public List<InRecipeCourseDto> mappingCourse(RecipeCourseDataMapper mapper){
        return courses.stream().map(mapper::toInRecipeCourseDto)
                .collect(Collectors.toList());
    }

    public List<InRecipeIngredientDto> mappingIngredient(RecipeIngredientDataMapper mapper, MyIngredientCollection myIngredients){
        return ingredients.stream()
                .map(ingredient ->
                        mapper.toInRecipeIngredientDto(ingredient,
                                myIngredients.checkOwnedIngredient(
                                        ingredient.getName(),
                                        ingredient.getTransVolume())))
                .collect(Collectors.toList());
    }
}
