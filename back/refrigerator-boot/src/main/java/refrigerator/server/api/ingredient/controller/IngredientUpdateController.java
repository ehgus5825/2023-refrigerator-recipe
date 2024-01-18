package refrigerator.server.api.ingredient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.api.global.common.BasicListRequestDTO;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.ingredient.application.port.in.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RegisterIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.FindRegisteredIngredientUseCase;
import refrigerator.server.api.ingredient.dto.IngredientRegisterRequestDTO;
import refrigerator.server.api.ingredient.dto.IngredientUpdateRequestDTO;
import refrigerator.back.ingredient.application.dto.IngredientRegisterDTO;
import refrigerator.back.ingredient.application.domain.entity.RegisteredIngredient;

import javax.validation.Valid;

import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;
import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@RestController
@RequiredArgsConstructor
public class IngredientUpdateController {

    final private FindRegisteredIngredientUseCase findRegisteredIngredientUseCase;

    final private RegisterIngredientUseCase registerIngredientUseCase;
    final private ModifyIngredientUseCase modifyIngredientUseCase;
    final private RemoveIngredientUseCase removeIngredientUseCase;

    private final GetMemberEmailUseCase memberInformation;

    @PostMapping("/api/ingredients/register")
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientRegisterDTO registerIngredient(
            @Valid @RequestBody IngredientRegisterRequestDTO request,
            BindingResult bindingResult) {

        multiCheck(bindingResult);

        RegisteredIngredient ingredientUnit = findRegisteredIngredientUseCase.getIngredient(request.getName());

        return registerIngredientUseCase.registerIngredient(request.getName(), request.getExpirationDate(), request.getVolume(),
                                                               ingredientUnit.getUnit(), request.getStorage(), ingredientUnit.getImage(), memberInformation.getMemberEmail());
    }

    @PutMapping("/api/ingredients/{ingredientId}/modify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyIngredient(
            @PathVariable("ingredientId") Long id,
            @Valid @RequestBody IngredientUpdateRequestDTO request,
            BindingResult bindingResult) {

        positiveCheck(id);
        multiCheck(bindingResult);

        modifyIngredientUseCase.modifyIngredient(id, request.getExpirationDate(), request.getVolume(), request.getStorage());
    }

    // TODO : Disabled?
    @DeleteMapping("/api/ingredients/{ingredientId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeIngredient(
             @PathVariable("ingredientId") Long id) {

        positiveCheck(id);

        removeIngredientUseCase.removeIngredient(id);
    }

    @DeleteMapping("/api/ingredients/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllIngredient(
            @Valid @RequestBody BasicListRequestDTO<Long> request,
            BindingResult bindingResult) {

        check(bindingResult, NOT_VALID_REQUEST_BODY);

        removeIngredientUseCase.removeAllIngredients(request.getData());
    }
}
