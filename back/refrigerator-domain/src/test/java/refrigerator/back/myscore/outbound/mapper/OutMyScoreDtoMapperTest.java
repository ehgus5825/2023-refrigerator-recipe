package refrigerator.back.myscore.outbound.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import refrigerator.back.myscore.application.dto.MyScoreDto;
import refrigerator.back.myscore.outbound.dto.OutMyScoreDto;

import static org.junit.jupiter.api.Assertions.*;

class OutMyScoreDtoMapperTest {

    OutMyScoreDtoMapper mapper = Mappers.getMapper(OutMyScoreDtoMapper.class);

    @Test
    @DisplayName("outMyScoreDto -> MyScoreDto")
    void toMyScoreDto(){
        // given
        Long scoreId = 1L;
        Double score = 3.5;
        OutMyScoreDto outDto = new OutMyScoreDto(scoreId, score);
        // when
        MyScoreDto result = mapper.toMyScoreDto(outDto);
        // then
        MyScoreDto expected = new MyScoreDto(scoreId, score);
        assertEquals(expected, result);
    }
}