package refrigerator.back.mybookmark.outbound.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;
import refrigerator.back.mybookmark.application.dto.MyBookmarkPreviewDto;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkDto;
import refrigerator.back.mybookmark.outbound.dto.OutMyBookmarkPreviewDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
        String recipeImage = "recipeImage";
        MyBookmarkDto result = mapper.toMyBookmarkDto(outDto, recipeImage);
        // then
        assertNotEquals(MyBookmarkDto.builder().build(), result);
        assertEquals(recipeImage, result.getRecipeImage());
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
        String recipeImage = "recipeImage";
        MyBookmarkPreviewDto result = mapper.toMyBookmarkPreviewDto(outDto, recipeImage);
        // then
        assertNotEquals(MyBookmarkPreviewDto.builder().build(), result);
        assertEquals(recipeImage, result.getRecipeImage());
    }
}