package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.outbound.mapper.OutRecipeIngredientMapper;
import refrigerator.back.ingredient.outbound.repository.SubIngredientQueryRepository;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.application.port.out.recipeIngredient.FindRecipeIngredientPort;


import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class RecipeIngredientAdapter implements FindRecipeIngredientPort {

    private final SubIngredientQueryRepository subIngredientQueryRepository;
    private final OutRecipeIngredientMapper mapper;

    @Override
    public List<RecipeIngredientDto> getRecipeIngredient(Long recipeId) {
        return subIngredientQueryRepository.getRecipeIngredient(recipeId)
                .stream()
                .map(mapper::toRecipeIngredientDto)
                .collect(Collectors.toList());
    }
}
