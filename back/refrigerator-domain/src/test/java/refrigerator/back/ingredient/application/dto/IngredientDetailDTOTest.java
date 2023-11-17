package refrigerator.back.ingredient.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class IngredientDetailDTOTest {

    @Test
    @DisplayName("식재료 상세 DTO 테스트")
    void ingredientDetailDTOTest() {

        LocalDate expirationDate = LocalDate.of(2023, 1, 1);

        IngredientDetailDTO dto = IngredientDetailDTO.builder()
                .ingredientID(1L)
                .name("감자")
                .storage(IngredientStorageType.FRIDGE)
                .registrationDate(expirationDate)
                .expirationDate(expirationDate)
                .volume(30.0)
                .unit("g")
                .image("test.png")
                .build();

        assertThat(dto.getIngredientID()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getStorage()).isEqualTo(IngredientStorageType.FRIDGE);
        assertThat(dto.getRegistrationDate()).isEqualTo(expirationDate);
        assertThat(dto.getExpirationDate()).isEqualTo(expirationDate);
        assertThat(dto.getVolume()).isEqualTo(30.0);
        assertThat(dto.getUnit()).isEqualTo("g");
        assertThat(dto.getImage()).isEqualTo("test.png");

        // case 1 : now - expirationDate < 0 (유통기한 안지남)
        LocalDate now = LocalDate.of(2022,12,31);

        dto.calculateRemainDays(now);

        assertThat(dto.getRemainDays()).isEqualTo("-1");

        // case 1 : now - expirationDate == 0
        now = LocalDate.of(2023,1,1);

        dto.calculateRemainDays(now);

        assertThat(dto.getRemainDays()).isEqualTo("0");

        // case 1 : now - expirationDate > 0 (유통기한 지남)
        now = LocalDate.of(2023,1,2);

        dto.calculateRemainDays(now);

        assertThat(dto.getRemainDays()).isEqualTo("+1");

    }
}