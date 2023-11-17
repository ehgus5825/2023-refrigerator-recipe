package refrigerator.back.ingredient.outbound.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.ingredient.outbound.dto.OutIngredientDTO;
import refrigerator.back.ingredient.outbound.dto.OutIngredientDetailDTO;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class OutIngredientMapperTest {

    OutIngredientMapper outIngredientMapper = Mappers.getMapper(OutIngredientMapper.class);

    @Test
    @DisplayName("OutIngredientDetailDTO에서 IngredientDetailDTO로 변환")
    void toIngredientDetailDtoTest() {

        OutIngredientDetailDTO outDto = OutIngredientDetailDTO.builder()
                .ingredientID(1L)
                .name("감자")
                .storage(IngredientStorageType.FRIDGE)
                .expirationDate(LocalDate.of(2023,1,1))
                .registrationDate(LocalDate.of(2023,1,1))
                .volume(30.0)
                .unit("g")
                .imageName("test.png")
                .build();

        IngredientDetailDTO dto = outIngredientMapper.toIngredientDetailDto(outDto, "/url/" + outDto.getImageName());

        assertThat(dto.getIngredientID()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getStorage()).isEqualTo(IngredientStorageType.FRIDGE);
        assertThat(dto.getRegistrationDate()).isEqualTo(LocalDate.of(2023,1,1));
        assertThat(dto.getExpirationDate()).isEqualTo(LocalDate.of(2023,1,1));
        assertThat(dto.getUnit()).isEqualTo("g");
        assertThat(dto.getVolume()).isEqualTo(30.0);
        assertThat(dto.getImage()).isEqualTo("/url/test.png");
        assertThat(dto.getRemainDays()).isNull();
    }

    @Test
    @DisplayName("OutIngredientDTO에서 IngredientDTO로 변환")
    void toIngredientDtoTest() {

        OutIngredientDTO outDto = OutIngredientDTO.builder()
                .ingredientID(1L)
                .name("감자")
                .expirationDate(LocalDate.of(2023,1,1))
                .imageName("test.png")
                .build();

        IngredientDTO dto = outIngredientMapper.toIngredientDto(outDto, "/url/" + outDto.getImageName());

        assertThat(dto.getIngredientID()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getImage()).isEqualTo("/url/test.png");
        assertThat(dto.getExpirationDate()).isEqualTo(LocalDate.of(2023,1,1));
        assertThat(dto.getRemainDays()).isNull();
    }
}