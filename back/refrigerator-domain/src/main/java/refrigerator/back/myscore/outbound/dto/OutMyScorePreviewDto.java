package refrigerator.back.myscore.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.myscore.application.dto.MyScorePreviewDto;
import refrigerator.back.myscore.outbound.mapper.OutMyScoreListDtoMapper;

import java.time.LocalDateTime;

@Getter
@Builder
public class OutMyScorePreviewDto {
    private Long scoreId;
    private Long recipeId;
    private String recipeImageName;
    private String recipeName;
    private LocalDateTime createDateTime;

    @QueryProjection
    public OutMyScorePreviewDto(Long scoreId, Long recipeId, String recipeImageName, String recipeName, LocalDateTime createDateTime) {
        this.scoreId = scoreId;
        this.recipeId = recipeId;
        this.recipeImageName = recipeImageName;
        this.recipeName = recipeName;
        this.createDateTime = createDateTime;
    }

    public MyScorePreviewDto mapping(OutMyScoreListDtoMapper mapper, ImageUrlConvert imageUrlConvert){
        return mapper.toMyScorePreviewDto(this, imageUrlConvert.getUrl(recipeImageName));
    }
}
