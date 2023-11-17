package refrigerator.server.api.ingredient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.server.api.global.common.BasicListRequestDTO;
import refrigerator.server.api.ingredient.dto.IngredientDeductionRequestDTO;
import refrigerator.server.api.ingredient.mapper.InIngredientMapper;
import refrigerator.back.ingredient.application.domain.RegisteredIngredient;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.port.in.ingredient.deducation.DeductionIngredientVolumeUseCase;
import refrigerator.back.ingredient.application.port.in.registeredIngredient.FindRegisteredIngredientUseCase;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;
import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@RestController
@RequiredArgsConstructor
public class IngredientDeductionController {

    private final DeductionIngredientVolumeUseCase deductionIngredientVolumeUseCase;
    private final FindRegisteredIngredientUseCase findRegisteredIngredientUseCase;

    private final GetMemberEmailUseCase memberInformation;
    private final InIngredientMapper mapper;

    @PutMapping("/api/ingredients/deduction")
    public void deductionIngredientVolume(@RequestBody @Valid BasicListRequestDTO<IngredientDeductionRequestDTO> request, BindingResult bindingResult){
        check(bindingResult, NOT_VALID_REQUEST_BODY);

        List<IngredientDeductionDTO> dtos = request.getData().stream()
                .map(mapper::toIngredientDeductionDTO).collect(Collectors.toList());

        IngredientDtoCheck(dtos);

        deductionIngredientVolumeUseCase.deduction(memberInformation.getMemberEmail(), dtos);
    }

    public void IngredientDtoCheck(List<IngredientDeductionDTO> data) {

        List<RegisteredIngredient> ingredientList = findRegisteredIngredientUseCase.getIngredientList();

        if(ingredientList.isEmpty()){
            throw new BusinessException(NO_PREVIOUSLY_REGISTERED_INGREDIENTS);
        }

        for (IngredientDeductionDTO dto : data) {
            if(ingredientList.stream().filter(i -> i.getName().equals(dto.getName()) && i.getUnit().equals(dto.getUnit()))
                    .collect(Collectors.toList()).size() != 1) {
                throw new BusinessException(NOT_VALID_INGREDIENTS_TO_BE_DEDUCTED);
            }
        }
    }
}
