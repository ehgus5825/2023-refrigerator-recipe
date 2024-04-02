package refrigerator.server.api.ingredient.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.server.api.global.common.BasicListRequestDTO;
import refrigerator.server.api.ingredient.dto.IngredientDeductionRequestDTO;
import refrigerator.server.api.ingredient.mapper.InIngredientMapper;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.port.in.DeductionIngredientVolumeUseCase;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.server.api.global.exception.ValidationExceptionHandler.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IngredientDeductionController {

    private final DeductionIngredientVolumeUseCase deductionIngredientVolumeUseCase;
    private final GetMemberEmailUseCase memberInformation;
    private final InIngredientMapper mapper;

    @PutMapping("/api/ingredients/deduction")
    public void deductionIngredientVolume(
            @Valid @RequestBody BasicListRequestDTO<IngredientDeductionRequestDTO> request,
            BindingResult bindingResult){

        multiCheck(bindingResult);

        List<IngredientDeductionDTO> dtos = request.getData().stream()
                .map(mapper::toIngredientDeductionDTO)
                .collect(Collectors.toList());

        deductionIngredientVolumeUseCase.deduction(memberInformation.getMemberEmail(), dtos);
    }
}
