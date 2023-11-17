package refrigerator.server.api.ingredient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.ingredient.application.port.in.matchByRecipe.MatchIngredientByRecipeUseCase;


@RestController
@RequiredArgsConstructor
public class OwnedRecipeIngredientController {

    private final MatchIngredientByRecipeUseCase matchIngredientByRecipeUseCase;
    private final GetMemberEmailUseCase memberInformation;

    @GetMapping("/api/ingredients/owned/{recipeId}")
    public BasicListResponseDTO<Long> getOwnedRecipeIngredients(@PathVariable("recipeId") Long recipeId){
        return new BasicListResponseDTO<>(matchIngredientByRecipeUseCase.getIngredientIds(memberInformation.getMemberEmail(), recipeId));
    }
}
