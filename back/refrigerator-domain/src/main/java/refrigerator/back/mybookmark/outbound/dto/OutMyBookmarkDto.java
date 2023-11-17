package refrigerator.back.mybookmark.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;
import refrigerator.back.mybookmark.outbound.mapper.OutMyBookmarkDtoMapper;

import java.time.LocalDateTime;

@Getter
@Builder
public class OutMyBookmarkDto {
    private Long bookmarkId;
    private Long recipeId;
    private String recipeImageName;
    private String recipeName;
    private Double scoreAvg;
    private Integer views;
    private LocalDateTime createDateTime;

    @QueryProjection
    public OutMyBookmarkDto(Long bookmarkId, Long recipeId, String recipeImageName, String recipeName, Double scoreAvg, Integer views, LocalDateTime createDateTime) {
        this.bookmarkId = bookmarkId;
        this.recipeId = recipeId;
        this.recipeImageName = recipeImageName;
        this.recipeName = recipeName;
        this.scoreAvg = scoreAvg;
        this.views = views;
        this.createDateTime = createDateTime;
    }

    public MyBookmarkDto mapping(OutMyBookmarkDtoMapper mapper, ImageUrlConvert imageUrlConvert){
        return mapper.toMyBookmarkDto(this, imageUrlConvert.getUrl(recipeImageName));
    }
}
