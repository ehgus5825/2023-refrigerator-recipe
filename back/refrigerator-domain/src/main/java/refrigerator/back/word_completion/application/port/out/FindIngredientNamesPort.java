package refrigerator.back.word_completion.application.port.out;

import java.util.List;

public interface FindIngredientNamesPort {
    List<String> getNamesByRecipe();
    List<String> getNamesByRegisteredIngredient();
}
