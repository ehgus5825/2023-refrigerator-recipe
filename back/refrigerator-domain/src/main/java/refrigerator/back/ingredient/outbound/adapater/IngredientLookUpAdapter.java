package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.ingredient.outbound.dto.OutIngredientDTO;
import refrigerator.back.ingredient.outbound.dto.OutIngredientDetailDTO;
import refrigerator.back.ingredient.outbound.repository.IngredientLookUpQueryRepository;
import refrigerator.back.ingredient.outbound.repository.IngredientPersistenceRepository;
import refrigerator.back.ingredient.application.domain.Ingredient;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.outbound.mapper.OutIngredientMapper;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientPort;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class IngredientLookUpAdapter implements FindIngredientListPort, FindIngredientPort {

    private final IngredientPersistenceRepository ingredientPersistenceRepository;
    private final IngredientLookUpQueryRepository ingredientLookUpQueryRepository;
    private final OutIngredientMapper mapper;
    private final ImageUrlConvert imageUrlConvert;

    @Override
    public Ingredient getIngredient(Long id) {
        return ingredientPersistenceRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_INGREDIENT));
    }

    @Override
    public List<Ingredient> getIngredients(String email) {
        return ingredientPersistenceRepository.findByEmailAndDeletedFalse(email);
    }

    @Override
    public IngredientDetailDTO getIngredientDetail(Long id) {
        OutIngredientDetailDTO dto = ingredientLookUpQueryRepository.findIngredient(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_INGREDIENT));

        return dto.mapping(mapper, imageUrlConvert);
    }

    @Override
    public List<IngredientDTO> getIngredientList(LocalDate now, IngredientSearchCondition condition, int page, int size) {
       return mapper(ingredientLookUpQueryRepository
               .findIngredientList(now, condition, PageRequest.of(page, size)));
    }

    @Override
    public List<IngredientDTO> getIngredientListOfAll(String email) {
        return mapper(ingredientLookUpQueryRepository
                .findIngredientListOfAll(email));
    }

    @Override
    public List<IngredientDTO> getIngredientListByDeadline(LocalDate now, Long days, String email) {
        return mapper(ingredientLookUpQueryRepository
                .findIngredientListByDeadline(now, days, email));
    }

    public List<IngredientDTO> mapper(List<OutIngredientDTO> ingredientListByDeadline) {
        return ingredientListByDeadline.stream()
                .map(dto -> dto.mapping(mapper, imageUrlConvert))
                .collect(Collectors.toList());
    }
}
