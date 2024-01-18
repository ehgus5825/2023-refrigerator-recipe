package refrigerator.server.api.ingredient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.ingredient.application.port.in.ProposeIngredientUseCase;
import refrigerator.server.api.ingredient.dto.IngredientProposeRequestDTO;

import javax.validation.Valid;

import static refrigerator.server.api.global.exception.ValidationExceptionHandler.multiCheck;

@RestController
@RequiredArgsConstructor
public class IngredientProposeController {

    final private ProposeIngredientUseCase proposeIngredientUseCase;

    private final GetMemberEmailUseCase memberInformation;

    @PostMapping("/api/ingredients/propose")
    @ResponseStatus(HttpStatus.CREATED)
    public void proposeIngredient(@Valid @RequestBody IngredientProposeRequestDTO request,
                                  BindingResult bindingResult) {

        multiCheck(bindingResult);

        proposeIngredientUseCase.proposeIngredient(request.getName(), request.getUnit(), memberInformation.getMemberEmail());
    }
}
