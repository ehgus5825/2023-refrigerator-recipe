package refrigerator.server.api.ingredient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.ingredient.application.port.in.MatchIngredientByRecipeUseCase;

import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Validated
public class OwnedRecipeIngredientController {

    private final MatchIngredientByRecipeUseCase matchIngredientByRecipeUseCase;
    private final GetMemberEmailUseCase memberInformation;

    // TODO : Disabled?
    @GetMapping("/api/ingredients/owned/{recipeId}")
    public BasicListResponseDTO<Long> getOwnedRecipeIngredients(
            @PathVariable("recipeId") @Positive Long recipeId){

        return new BasicListResponseDTO<>(matchIngredientByRecipeUseCase.getIngredientIds(
                memberInformation.getMemberEmail(), recipeId));
    }
}
