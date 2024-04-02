package refrigerator.back.mybookmark.outbound.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;
import refrigerator.back.mybookmark.application.dto.MyBookmarkPreviewDto;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkDto;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkPreviewDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class OutBookmarkDtoMapperTest {

    OutMyBookmarkDtoMapper mapper = Mappers.getMapper(OutMyBookmarkDtoMapper.class);

    @Test
    @DisplayName("outBookmarkDto -> bookmarkDto")
    void toMyBookmarkDto() {
        // given
        String recipeImageName = "recipeImageName";
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 10, 10, 50);
        OutMyBookmarkDto outDto = new OutMyBookmarkDto(
                1L,
                1L,
                recipeImageName,
                "recipeName",
                4.5,
                5,
                createDateTime);
        // when
        MyBookmarkDto result = mapper.toMyBookmarkDto(outDto);
        // then
        assertNotEquals(MyBookmarkDto.builder().build(), result);

        log.info("{}", result.getRecipeImage());
        assertEquals(recipeImageName, result.getRecipeImage());
    }

    @Test
    @DisplayName("outBookmarkPreviewDto -> bookmarkPreviewDto")
    void toMyBookmarkPreviewDto() {
        // given
        String recipeImageName = "recipeImageName";
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 10, 10, 50);
        OutMyBookmarkPreviewDto outDto = new OutMyBookmarkPreviewDto(
                1L,
                1L,
                recipeImageName,
                "recipeName",
                createDateTime);
        // when
        MyBookmarkPreviewDto result = mapper.toMyBookmarkPreviewDto(outDto);
        // then
        assertNotEquals(MyBookmarkPreviewDto.builder().build(), result);
        assertEquals(recipeImageName, result.getRecipeImage());
    }
}