package refrigerator.server.api.ingredient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.ingredient.application.port.in.ingredient.update.ModifyIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.ingredient.update.RegisterIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.ingredient.update.RemoveIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.registeredIngredient.FindRegisteredIngredientUseCase;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.server.api.ingredient.dto.IngredientRegisterRequestDTO;
import refrigerator.server.api.ingredient.dto.IngredientUpdateRequestDTO;
import refrigerator.back.ingredient.application.dto.IngredientRegisterDTO;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;


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

    @PostMapping("/api/ingredients")
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientRegisterDTO registerIngredient(@RequestBody @Valid IngredientRegisterRequestDTO request, BindingResult bindingResult) {
        check(bindingResult, NOT_VALID_REQUEST_BODY);

        RegisteredIngredient ingredientUnit = findRegisteredIngredientUseCase.getIngredient(request.getName());

        return registerIngredientUseCase.registerIngredient(request.getName(), request.getExpirationDate(), request.getVolume(),
                                                               ingredientUnit.getUnit(), request.getStorage(), ingredientUnit.getImage(), memberInformation.getMemberEmail());
    }

    @PutMapping("/api/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyIngredient(@PathVariable("ingredientId") Long id, @RequestBody @Valid IngredientUpdateRequestDTO request, BindingResult bindingResult) {
        check(bindingResult, NOT_VALID_REQUEST_BODY);

        modifyIngredientUseCase.modifyIngredient(id, request.getExpirationDate(), request.getVolume(), request.getStorage());
    }

    @DeleteMapping("/api/ingredients/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeIngredient(@PathVariable("ingredientId") Long id) {
        removeIngredientUseCase.removeIngredient(id);
    }

    @DeleteMapping("/api/ingredients")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllIngredient(@RequestBody @Valid BasicListResponseDTO<Long> request, BindingResult bindingResult) {
        check(bindingResult, NOT_VALID_REQUEST_BODY);

        removeIngredientUseCase.removeAllIngredients(request.getData());
    }
}
