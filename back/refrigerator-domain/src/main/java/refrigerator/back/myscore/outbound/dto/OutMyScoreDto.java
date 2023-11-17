package refrigerator.back.myscore.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import refrigerator.back.myscore.application.dto.MyScoreDto;
import refrigerator.back.myscore.outbound.mapper.OutMyScoreDtoMapper;
import refrigerator.back.myscore.outbound.mapper.OutMyScoreListDtoMapper;

@Getter
public class OutMyScoreDto {

    private Long scoreId;
    private Double score;

    @QueryProjection
    public OutMyScoreDto(Long scoreId, Double score) {
        this.scoreId = scoreId;
        this.score = score;
    }

    public MyScoreDto mapping(OutMyScoreDtoMapper mapper){
        return mapper.toMyScoreDto(this);
    }
}
