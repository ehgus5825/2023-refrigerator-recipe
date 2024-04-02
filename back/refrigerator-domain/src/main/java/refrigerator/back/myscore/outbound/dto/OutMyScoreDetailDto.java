package refrigerator.back.myscore.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.myscore.application.dto.MyScoreDetailDto;
import refrigerator.back.myscore.outbound.mapper.OutMyScoreListDtoMapper;

import java.time.LocalDateTime;

@Getter
@Builder
public class OutMyScoreDetailDto {
    private Long scoreId;
    private Long recipeId;
    private String recipeImageName;
    private String recipeName;
    private Double myScore;
    private LocalDateTime createDateTime;
    private Double scoreAvg;
    private Integer views;

    @QueryProjection
    public OutMyScoreDetailDto(Long scoreId, Long recipeId, String recipeImageName, String recipeName, Double myScore, LocalDateTime createDateTime, Double scoreAvg, Integer views) {
        this.scoreId = scoreId;
        this.recipeId = recipeId;
        this.recipeImageName = recipeImageName;
        this.recipeName = recipeName;
        this.myScore = myScore;
        this.createDateTime = createDateTime;
        this.scoreAvg = scoreAvg;
        this.views = views;
    }

    public MyScoreDetailDto mapping(OutMyScoreListDtoMapper mapper){
        return mapper.toMyScoreDetailDto(this);
    }
}
