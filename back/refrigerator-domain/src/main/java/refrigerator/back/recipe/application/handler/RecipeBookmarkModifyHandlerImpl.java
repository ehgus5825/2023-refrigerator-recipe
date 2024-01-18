package refrigerator.back.recipe.application.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refrigerator.back.mybookmark.application.handler.RecipeBookmarkModifyHandler;
import refrigerator.back.recipe.application.port.out.UpdateRecipeBookmarkPort;

@Service
@RequiredArgsConstructor
public class RecipeBookmarkModifyHandlerImpl implements RecipeBookmarkModifyHandler {

    private final UpdateRecipeBookmarkPort updateRecipeBookmarkPort;

    @Override
    public void added(Long recipeId) {
        updateRecipeBookmarkPort.add(recipeId);
    }

    @Override
    public void deleted(Long recipeId) {
        updateRecipeBookmarkPort.delete(recipeId);
    }

}
