package refrigerator.server.api.ingredient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.ingredient.application.port.in.suggestIngredient.ProposeIngredientUseCase;
import refrigerator.server.api.ingredient.dto.IngredientProposeRequestDTO;

import javax.validation.Valid;

import static refrigerator.server.api.global.exception.ValidationExceptionHandler.check;
import static refrigerator.back.ingredient.exception.IngredientExceptionType.NOT_VALID_REQUEST_BODY;

@RestController
@RequiredArgsConstructor
public class IngredientProposeController {

    final private ProposeIngredientUseCase proposeIngredientUseCase;

    private final GetMemberEmailUseCase memberInformation;

    @PostMapping("/api/ingredients/propose")
    @ResponseStatus(HttpStatus.CREATED)
    public void proposeIngredient(@RequestBody @Valid IngredientProposeRequestDTO request, BindingResult bindingResult) {
        check(bindingResult, NOT_VALID_REQUEST_BODY);

        proposeIngredientUseCase.proposeIngredient(request.getName(), request.getUnit(), memberInformation.getMemberEmail());
    }
}
