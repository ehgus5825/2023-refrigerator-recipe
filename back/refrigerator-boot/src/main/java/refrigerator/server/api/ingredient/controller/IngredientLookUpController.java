package refrigerator.server.api.ingredient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.server.api.ingredient.mapper.InIngredientMapper;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.IngredientUnitDTO;
import refrigerator.back.ingredient.application.domain.value.IngredientStorageType;
import refrigerator.back.ingredient.application.port.in.FindIngredientUseCase;
import refrigerator.back.ingredient.application.port.in.FindRegisteredIngredientUseCase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class IngredientLookUpController {

    private final FindIngredientUseCase findIngredientDetailUseCase;
    private final FindIngredientUseCase findIngredientUseCase;
    private final FindRegisteredIngredientUseCase findRegisteredIngredientUseCase;

    private final GetMemberEmailUseCase memberInformation;
    private final InIngredientMapper mapper;

    @GetMapping("/api/ingredients/search/unit")
    public IngredientUnitDTO findIngredientUnit(
            @RequestParam(value = "name") @NotBlank String name) {

        return mapper.toIngredientUnitResponseDTO(findRegisteredIngredientUseCase.getIngredient(name));
    }

    @GetMapping("/api/ingredients/search")
    public BasicListResponseDTO<IngredientDTO> findIngredientList(
            @RequestParam(value = "storage", defaultValue = "냉장") String storage,
            @RequestParam(value = "deadline", defaultValue = "false") Boolean deadline,
            @RequestParam(value = "page", defaultValue = "0") @PositiveOrZero Integer page,
            @RequestParam(value = "size", defaultValue = "12") @Positive Integer size) {

        List<IngredientDTO> ingredientList = findIngredientUseCase.getIngredientList(
                mapper.toIngredientSearchCondition(IngredientStorageType.from(storage), deadline,
                        memberInformation.getMemberEmail()), page, size);

        return new BasicListResponseDTO<>(ingredientList);
    }

    @GetMapping("/api/ingredients/search/all")
    public BasicListResponseDTO<IngredientDTO> searchIngredientList() {

        List<IngredientDTO> ingredientList = findIngredientUseCase
                .getIngredientListOfAll(memberInformation.getMemberEmail());

        return new BasicListResponseDTO<>(ingredientList);
    }

    @GetMapping("/api/ingredients/search/deadline/{days}")
    public BasicListResponseDTO<IngredientDTO> findIngredientListByDeadline(
             @PathVariable("days") @PositiveOrZero Long days) {

        List<IngredientDTO> ingredientList = findIngredientUseCase
                .getIngredientListByDeadline(days, memberInformation.getMemberEmail());

        return new BasicListResponseDTO<>(ingredientList);
    }

    @GetMapping("/api/ingredients/{ingredientId}/details")
    public IngredientDetailDTO findIngredient(
             @PathVariable("ingredientId") @Positive Long id) {

        return findIngredientDetailUseCase.getIngredient(id);
    }
}
