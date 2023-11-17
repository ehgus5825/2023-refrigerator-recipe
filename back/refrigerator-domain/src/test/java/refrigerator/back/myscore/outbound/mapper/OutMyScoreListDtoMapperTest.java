package refrigerator.back.myscore.outbound.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.myscore.application.dto.MyScoreDetailDto;
import refrigerator.back.myscore.application.dto.MyScorePreviewDto;
import refrigerator.back.myscore.outbound.dto.OutMyScoreDetailDto;
import refrigerator.back.myscore.outbound.dto.OutMyScorePreviewDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OutMyScoreListDtoMapperTest {

    OutMyScoreListDtoMapper mapper = Mappers.getMapper(OutMyScoreListDtoMapper.class);

    @Test
    @DisplayName("outMyScoreDetailDto -> MyScoreDetailDto")
    void toMyScoreDetailDto() {
        // given
        Long scoreId = 1L;
        Long recipeId = 1L;
        String recipeImage = "recipeImage";
        String recipeName = "recipeName";
        Double score = 4.5;
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 7, 1, 1);
        OutMyScoreDetailDto outDto = new OutMyScoreDetailDto(scoreId, recipeId, recipeImage, recipeName, score, createDateTime);
        // when
        String recipeImageUrl = "recipeImageUrl";
        MyScoreDetailDto result = mapper.toMyScoreDetailDto(outDto, recipeImageUrl);
        // then
        MyScoreDetailDto expected = new MyScoreDetailDto(scoreId, recipeId, recipeImageUrl, recipeName, score);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("outMyScorePreviewDto -> MyScorePreviewDto")
    void toMyScorePreviewDto() {
        // given
        Long scoreId = 1L;
        Long recipeId = 1L;
        String recipeImage = "recipeImage";
        String recipeName = "recipeName";
        LocalDateTime createDateTime = LocalDateTime.of(2023, 7, 7, 1, 1);
        OutMyScorePreviewDto outDto = new OutMyScorePreviewDto(scoreId, recipeId, recipeImage, recipeName, createDateTime);
        // when
        String recipeImageUrl = "recipeImageUrl";
        MyScorePreviewDto result = mapper.toMyScorePreviewDto(outDto, recipeImageUrl);
        // then
        MyScorePreviewDto expected = new MyScorePreviewDto(scoreId, recipeId, recipeImageUrl, recipeName);
        assertEquals(expected, result);
    }


}