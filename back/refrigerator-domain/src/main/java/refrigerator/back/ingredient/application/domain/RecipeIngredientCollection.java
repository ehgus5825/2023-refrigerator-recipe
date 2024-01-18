package refrigerator.back.ingredient.application.domain;

import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.NOT_FOUND_REGISTERED_RECIPE;

public class RecipeIngredientCollection {

    List<RecipeIngredientDto> recipeIngredients;

    public RecipeIngredientCollection(List<RecipeIngredientDto> recipeIngredients){
        this.recipeIngredients = recipeIngredients;
        validationEmpty();
    }

    private void validationEmpty(){
        if (recipeIngredients.isEmpty()){
            throw new BusinessException(NOT_FOUND_REGISTERED_RECIPE);
        }
    }

    public List<Long> getIds(List<MyIngredientDTO> myIngredients){

        Map<String, Boolean> nameMap =  myIngredients
                .stream()
                .collect(Collectors.toMap(MyIngredientDTO::getName, ingredient -> true));

        return recipeIngredients.stream()
                .filter(item -> nameMap.getOrDefault(item.getName(), false))
                .map(RecipeIngredientDto::getIngredientId)
                .collect(Collectors.toList());
    }
}
