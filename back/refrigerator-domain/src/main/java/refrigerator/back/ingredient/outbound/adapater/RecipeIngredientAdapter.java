package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.ingredient.outbound.mapper.OutSubIngredientMapper;
import refrigerator.back.ingredient.outbound.repository.query.SubIngredientQueryRepository;
import refrigerator.back.ingredient.application.dto.RecipeIngredientDto;
import refrigerator.back.ingredient.application.port.out.FindRecipeIngredientPort;


import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class RecipeIngredientAdapter implements FindRecipeIngredientPort {

    private final SubIngredientQueryRepository subIngredientQueryRepository;
    private final OutSubIngredientMapper mapper;

    @Override
    public List<RecipeIngredientDto> getRecipeIngredient(Long recipeId) {
        return subIngredientQueryRepository.getRecipeIngredient(recipeId)
                .stream()
                .map(mapper::toRecipeIngredientDto)
                .collect(Collectors.toList());
    }
}
