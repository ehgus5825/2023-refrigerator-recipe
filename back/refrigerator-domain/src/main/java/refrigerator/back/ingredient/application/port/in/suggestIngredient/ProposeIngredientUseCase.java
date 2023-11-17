package refrigerator.back.ingredient.application.port.in.suggestIngredient;

public interface ProposeIngredientUseCase {

    Long proposeIngredient(String name, String capacityUnit, String email);
}
