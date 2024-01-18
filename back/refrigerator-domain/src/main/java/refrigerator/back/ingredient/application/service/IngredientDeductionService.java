package refrigerator.back.ingredient.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.domain.MyIngredientCollection;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;
import refrigerator.back.ingredient.application.port.in.DeductionIngredientVolumeUseCase;
import refrigerator.back.ingredient.application.port.out.FindSubIngredientPort;
import refrigerator.back.ingredient.application.port.out.UpdateVolumeIngredientPort;

import java.time.LocalDate;
import java.util.List;

// TODO : 일급 컬렉션 변경

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientDeductionService implements DeductionIngredientVolumeUseCase {

    private final FindSubIngredientPort findSubIngredientPort;
    private final UpdateVolumeIngredientPort updatevolumeIngredientPort;
    private final CurrentTime<LocalDate> currentTime;

    @Override
    public void deduction(String memberId, List<IngredientDeductionDTO> ingredientDTOList) {

        List<MyIngredientDTO> MyIngredients = findSubIngredientPort.getAvailableIngredients(memberId, currentTime.now());
        MyIngredientCollection collection = new MyIngredientCollection(MyIngredients, ingredientDTOList);
        collection.deduction(updatevolumeIngredientPort);
    }
}
