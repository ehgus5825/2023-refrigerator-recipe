package refrigerator.server.api.word_completion.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.back.word_completion.application.port.in.IngredientWordCompletionUseCase;
import refrigerator.back.word_completion.application.port.in.RecipeWordCompletionUseCase;


@RestController
@RequiredArgsConstructor
public class WordCompletionController {

    private final RecipeWordCompletionUseCase recipeWordCompletionUseCase;
    private final IngredientWordCompletionUseCase ingredientWordCompletionUseCase;

    @GetMapping("/api/recipe/search/word-completion")
    public BasicListResponseDTO<String> getRecipeWordCompletion(@RequestParam("keyword") String keyword){
        return new BasicListResponseDTO<>(recipeWordCompletionUseCase.search(keyword));
    }

    @GetMapping("/api/ingredient/search/word-completion")
    public BasicListResponseDTO<String> getIngredientWordCompletion(@RequestParam("keyword") String keyword){
        return new BasicListResponseDTO<>(ingredientWordCompletionUseCase.search(keyword));
    }
}
