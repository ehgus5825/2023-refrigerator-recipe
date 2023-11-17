package refrigerator.back.ingredient.outbound.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.global.s3.S3TestConfiguration;
import refrigerator.back.ingredient.application.domain.IngredientStorageType;
import refrigerator.back.ingredient.application.dto.IngredientDTO;
import refrigerator.back.ingredient.application.dto.IngredientDetailDTO;
import refrigerator.back.ingredient.outbound.mapper.OutIngredientMapper;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        initializers = ConfigDataApplicationContextInitializer.class,
        classes = S3TestConfiguration.class)
@Slf4j
class OutIngredientDTOTest {

    OutIngredientMapper outIngredientMapper = Mappers.getMapper(OutIngredientMapper.class);

    @Autowired ImageUrlConvert imageUrlConvert;

    @Test
    @DisplayName("식재료 outDto 테스트")
    void outIngredientDtoTest() {
        OutIngredientDTO dto = OutIngredientDTO.builder()
                .ingredientID(1L)
                .name("감자")
                .expirationDate(LocalDate.of(2023, 1, 1))
                .imageName("test.png")
                .build();

        assertThat(dto.getIngredientID()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getImageName()).isEqualTo("test.png");
        assertThat(dto.getExpirationDate()).isEqualTo(LocalDate.of(2023, 1, 1));
    }

    @Test
    @DisplayName("OutIngredientDetailDTO 매핑 테스트")
    void mappingTest() {
        OutIngredientDTO outDto = OutIngredientDTO.builder()
                .ingredientID(1L)
                .name("감자")
                .expirationDate(LocalDate.of(2023, 1, 1))
                .imageName("IMAGE_INGREDIENT_PROCESSED.png")
                .build();

        IngredientDTO dto = outDto.mapping(outIngredientMapper, imageUrlConvert);

        assertThat(dto.getIngredientID()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("감자");
        assertThat(dto.getExpirationDate()).isEqualTo(LocalDate.of(2023, 1, 1));
        log.info(dto.getImage());
    }
}