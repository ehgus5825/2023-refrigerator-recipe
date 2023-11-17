package refrigerator.back.recipe.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.recipe.application.port.in.AddRecipeViewUseCase;
import refrigerator.back.recipe.application.port.out.UpdateRecipeViewsPort;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeViewAddService implements AddRecipeViewUseCase {

    private final UpdateRecipeViewsPort updateRecipeViewsPort;

    @Override
    public void add(Long recipeId) {
        updateRecipeViewsPort.add(recipeId);
    }
}
