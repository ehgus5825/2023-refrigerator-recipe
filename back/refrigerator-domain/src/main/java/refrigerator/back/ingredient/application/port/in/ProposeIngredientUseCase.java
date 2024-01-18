package refrigerator.back.ingredient.application.port.in;

public interface ProposeIngredientUseCase {

    Long proposeIngredient(String name, String capacityUnit, String email);
}
