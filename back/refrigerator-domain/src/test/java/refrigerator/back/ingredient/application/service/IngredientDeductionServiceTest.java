package refrigerator.back.ingredient.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.dto.IngredientDeductionDTO;
import refrigerator.back.ingredient.application.dto.MyIngredientDTO;
import refrigerator.back.ingredient.application.port.out.FindSubIngredientPort;
import refrigerator.back.ingredient.application.port.out.UpdateVolumeIngredientPort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientDeductionServiceTest {
    
    @InjectMocks IngredientDeductionService ingredientDeductionService;

    @Mock FindSubIngredientPort findSubIngredientPort;

    @Mock UpdateVolumeIngredientPort updatevolumeIngredientPort;


    @Mock CurrentTime<LocalDate> currentTime;

    @Test
    @DisplayName("식재료 차감 : 기존 용량이 차감 용량보다 큰 경우")
    void deductionTest_case1() {

        // given
        String memberId = "email123@gmail.com";
        LocalDate now = LocalDate.of(2022, 12, 31);

        List<MyIngredientDTO> ingredients = new ArrayList<>(List.of(MyIngredientDTO.builder()
                .id(1L)
                .name("감자")
                .volume(30.0)
                .unit("g")
                .build()));

        given(findSubIngredientPort.getAvailableIngredients(memberId, now))
                .willReturn(ingredients);

        given(currentTime.now())
                .willReturn(now);

        List<IngredientDeductionDTO> dtos = new ArrayList<>(List.of(IngredientDeductionDTO.builder()
                .name("감자")
                .volume(15.0)
                .unit("g")
                .build()));

        doNothing().when(updatevolumeIngredientPort).updateVolume(1L,15.0);

        // when
        ingredientDeductionService.deduction(memberId, dtos);
    }
    
    @Test
    @DisplayName("식재료 차감 : 기존 용량이 차감 용량보다 작은 경우")
    void deductionTest_case2() {

        // given
        String memberId = "email123@gmail.com";
        LocalDate now = LocalDate.of(2022, 12, 31);
        LocalDate expirationDate = LocalDate.of(2023,1,1);

        List<MyIngredientDTO> ingredients = new ArrayList<>(List.of(MyIngredientDTO.builder()
                .id(1L)
                .name("감자")
                .volume(30.0)
                .unit("g")
                .build()));
        
        given(findSubIngredientPort.getAvailableIngredients(memberId, now))
                .willReturn(ingredients);

        given(currentTime.now())
                .willReturn(now);

        List<IngredientDeductionDTO> dtos = new ArrayList<>(List.of(IngredientDeductionDTO.builder()
                .unit("g")
                .name("감자")
                .volume(40.0)
                .build()));

        doNothing().when(updatevolumeIngredientPort).updateVolume(1L,0.0);

        // when
        ingredientDeductionService.deduction(memberId, dtos);
    }

    @Test
    @DisplayName("식재료 차감 실패 : 유통기한 지난 식재료")
    void deductionTest_case3() {

        // given
        String memberId = "email123@gmail.com";
        LocalDate now = LocalDate.of(2023, 1, 2);

        List<MyIngredientDTO> ingredients = new ArrayList<>(List.of(MyIngredientDTO.builder()
                .id(1L)
                .name("감자")
                .volume(30.0)
                .unit("g")
                .build()));

        given(findSubIngredientPort.getAvailableIngredients(memberId, now))
                .willReturn(new ArrayList<>());

        given(currentTime.now())
                .willReturn(now);

        List<IngredientDeductionDTO> dtos = new ArrayList<>(List.of(IngredientDeductionDTO.builder()
                .unit("g")
                .name("감자")
                .volume(40.0)
                .build()));

        // when, then
        assertThatThrownBy(() -> ingredientDeductionService.deduction(memberId, dtos))
                .isInstanceOf(BusinessException.class);
    }
}