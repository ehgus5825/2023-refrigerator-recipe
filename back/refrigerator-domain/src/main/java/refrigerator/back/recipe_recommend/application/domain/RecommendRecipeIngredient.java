package refrigerator.back.recipe_recommend.application.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import refrigerator.back.recipe.application.domain.value.RecipeIngredientType;
import refrigerator.back.recipe_recommend.application.dto.MyIngredientDto;


@Getter
@Builder
@EqualsAndHashCode
@Slf4j
public class RecommendRecipeIngredient {

    private Long recipeId;
    private String name;
    private RecipeIngredientType type;
    private Double volume;
    private String unit;

    public int getMatchedIngredientTypeWeight(MyIngredientDto myIngredient){
        if (myIngredient != null && isMatched(myIngredient)){
            return type.getWeight();
        }
        return 0;
    }

    protected int getIngredientTypeWeight(){
        return type.getWeight();
    }

    private boolean isMatched(MyIngredientDto myIngredient) {
        return myIngredient.getName().equals(name) &&
                myIngredient.getVolume() >= volume &&
                myIngredient.getUnit().equals(unit);
    }
}
