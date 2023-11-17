package refrigerator.back.ingredient.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.ingredient.application.domain.IngredientSearchCondition;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientListPort;
import refrigerator.back.ingredient.application.port.out.ingredient.lookUp.FindIngredientPort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientLookUpServiceTest {

    @InjectMocks IngredientLookUpService ingredientLookUpService;

    @Mock FindIngredientListPort findIngredientListPort;

    @Mock FindIngredientPort findIngredientPort;

    @Mock CurrentTime<LocalDate> currentTime;

    @Test
    @DisplayName("식재료 목록 조회 테스트")
    void getIngredientListTest() {

        LocalDate now = LocalDate.of(2023,1,1);

        IngredientSearchCondition condition = IngredientSearchCondition.builder()
                .deadline(false)
                .storage(IngredientStorageType.FRIDGE)
                .email("email123@gmail.com")
                .build();

        IngredientDTO.IngredientDTOBuilder builder = IngredientDTO.builder()
                .image("test.png")
                .expirationDate(now);

        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        ingredientDTOList.add(builder.ingredientID(1L).name("감자").build());
        ingredientDTOList.add(builder.ingredientID(2L).name("고구마").build());
        ingredientDTOList.add(builder.ingredientID(3L).name("자색고구마").build());
        ingredientDTOList.add(builder.ingredientID(4L).name("옥수수").build());

        given(currentTime.now())
                .willReturn(now);

        given(findIngredientListPort.getIngredientList(now, condition, 0, 10))
                .willReturn(ingredientDTOList);

        List<IngredientDTO> ingredientList = ingredientLookUpService.getIngredientList(condition, 0, 10);
        assertThat(ingredientList.size()).isEqualTo(4);
        for (int i = 0; i < ingredientList.size(); i++) {
            assertThat(ingredientList.get(i).getIngredientID()).isEqualTo(i + 1L);
            assertThat(ingredientList.get(i).getRemainDays()).isEqualTo("0");
        }
    }

    @Test
    @DisplayName("이메일에 따른 전체 식재료 조회 테스트")
    void getIngredientListOfAllTest() {

        String memberID = "email123@gmail.com";
        LocalDate now = LocalDate.of(2023,1,1);

        List<IngredientDTO> ingredientDTOList = new ArrayList<>();

        IngredientDTO.IngredientDTOBuilder builder = IngredientDTO.builder()
                .expirationDate(now)
                .image("test.png");

        ingredientDTOList.add(builder.ingredientID(1L).name("감자").build());
        ingredientDTOList.add(builder.ingredientID(2L).name("고구마").build());
        ingredientDTOList.add(builder.ingredientID(3L).name("자색고구마").build());
        ingredientDTOList.add(builder.ingredientID(4L).name("옥수수").build());

        given(currentTime.now())
                .willReturn(now);

        given(findIngredientListPort.getIngredientListOfAll(memberID))
                .willReturn(ingredientDTOList);

        List<IngredientDTO> ingredientListOfAll = ingredientLookUpService.getIngredientListOfAll(memberID);

        assertThat(ingredientListOfAll.size()).isEqualTo(4);
        for (int i = 0; i < ingredientListOfAll.size(); i++) {
            assertThat(ingredientListOfAll.get(i).getIngredientID()).isEqualTo(i + 1L);
            assertThat(ingredientListOfAll.get(i).getRemainDays()).isEqualTo("0");
        }
    }

    @Test
    @DisplayName("임박 식재료 목록 조회 테스트")
    void getIngredientListByDeadlineTest() {

        LocalDate now = LocalDate.of(2023,1,1);
        String memberID = "email123@gmail.com";

        List<IngredientDTO> ingredientDTOList = new ArrayList<>();

        IngredientDTO.IngredientDTOBuilder builder = IngredientDTO.builder()
                .expirationDate(now.plusDays(1))
                .image("test.png");

        ingredientDTOList.add(builder.ingredientID(1L).name("감자").build());
        ingredientDTOList.add(builder.ingredientID(2L).name("고구마").build());
        ingredientDTOList.add(builder.ingredientID(3L).name("자색고구마").build());
        ingredientDTOList.add(builder.ingredientID(4L).name("옥수수").build());

        given(currentTime.now())
                .willReturn(now);

        given(findIngredientListPort.getIngredientListByDeadline(now, 1L, memberID))
                .willReturn(ingredientDTOList);

        List<IngredientDTO> ingredientListByDeadline = ingredientLookUpService.getIngredientListByDeadline(1L, memberID);

        assertThat(ingredientListByDeadline.size()).isEqualTo(4);
        for (int i = 0; i < ingredientListByDeadline.size(); i++) {
            assertThat(ingredientListByDeadline.get(i).getIngredientID()).isEqualTo(i + 1L);
            assertThat(ingredientListByDeadline.get(i).getRemainDays()).isEqualTo("-1");
        }
    }

    @Test
    @DisplayName("식재료 단건 조회 테스트")
    void getIngredientTest() {

        LocalDate now = LocalDate.of(2023,1,1);

        IngredientDetailDTO dto = IngredientDetailDTO.builder()
                .ingredientID(1L)
                .name("감자")
                .registrationDate(now)
                .expirationDate(now)
                .storage(IngredientStorageType.FRIDGE)
                .image("test.png")
                .volume(30.0)
                .unit("g")
                .build();

        given(findIngredientPort.getIngredientDetail(1L))
                .willReturn(dto);

        given(currentTime.now())
                .willReturn(now);

        IngredientDetailDTO ingredientDetail = ingredientLookUpService.getIngredient(1L);

        assertThat(ingredientDetail.getIngredientID()).isEqualTo(1L);
        assertThat(ingredientDetail.getName()).isEqualTo("감자");
        assertThat(ingredientDetail.getExpirationDate()).isEqualTo(now);
        assertThat(ingredientDetail.getRegistrationDate()).isEqualTo(now);
        assertThat(ingredientDetail.getVolume()).isEqualTo(30.0);
        assertThat(ingredientDetail.getUnit()).isEqualTo("g");
        assertThat(ingredientDetail.getStorage()).isEqualTo(IngredientStorageType.FRIDGE);
        assertThat(ingredientDetail.getImage()).isEqualTo("test.png");
        assertThat(ingredientDetail.getRemainDays()).isEqualTo("0");
    }
}