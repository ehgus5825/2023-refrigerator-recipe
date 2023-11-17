package refrigerator.back.recipe.outbound.mapper;

import refrigerator.back.recipe.application.dto.MyIngredientDto;
import refrigerator.back.recipe.outbound.dto.OutMyIngredientDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutMyIngredientDtoMappingCollection {

    private final List<OutMyIngredientDto> myIngredients;

    public OutMyIngredientDtoMappingCollection(List<OutMyIngredientDto> myIngredients) {
        this.myIngredients = myIngredients;
    }

    public Map<String, MyIngredientDto> makeMyIngredientTable(OutMyIngredientDtoMapper mapper){
        Map<String, MyIngredientDto> table = new HashMap<>();
        myIngredients.forEach(ingredient -> {
            table.put(ingredient.getName(), ingredient.mapping(mapper));
        });
        return table;
    }
}
