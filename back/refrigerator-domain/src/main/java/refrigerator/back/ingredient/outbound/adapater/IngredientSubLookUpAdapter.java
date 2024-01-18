package refrigerator.back.ingredient.outbound.adapater;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.ingredient.application.domain.entity.Ingredient;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;
import refrigerator.back.ingredient.application.port.out.FindSubIngredientPort;
import refrigerator.back.ingredient.outbound.mapper.OutSubIngredientMapper;
import refrigerator.back.ingredient.outbound.repository.jpa.IngredientPersistenceRepository;
import refrigerator.back.ingredient.outbound.repository.query.IngredientLookUpQueryRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static refrigerator.back.ingredient.exception.IngredientExceptionType.NOT_FOUND_INGREDIENT;

@Slf4j
@Component
@RequiredArgsConstructor
public class IngredientSubLookUpAdapter implements FindSubIngredientPort {

    private final IngredientPersistenceRepository ingredientPersistenceRepository;
    private final IngredientLookUpQueryRepository ingredientLookUpQueryRepository;
    private final OutSubIngredientMapper outSubIngredientMapper;

    @Override
    public List<MyIngredientDTO> getAvailableIngredients(String email, LocalDate date) {
        return ingredientLookUpQueryRepository.findAvailableIngredients(email, date)
                .stream().map(dto -> dto.mapping(outSubIngredientMapper))
                .collect(Collectors.toList());
    }


    @Override
    public Ingredient getIngredient(Long id) {
        return ingredientPersistenceRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_INGREDIENT));
    }
}
