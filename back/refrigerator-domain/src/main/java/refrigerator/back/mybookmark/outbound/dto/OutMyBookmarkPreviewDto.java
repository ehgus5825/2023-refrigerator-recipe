package refrigerator.back.mybookmark.outbound.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.mybookmark.application.dto.MyBookmarkDto;
import refrigerator.back.mybookmark.application.dto.MyBookmarkPreviewDto;
import refrigerator.back.mybookmark.outbound.mapper.OutMyBookmarkDtoMapper;

import java.time.LocalDateTime;

@Getter
@Builder
public class OutMyBookmarkPreviewDto {
    private Long bookmarkId;
    private Long recipeId;
    private String recipeImageName;
    private String recipeName;
    private LocalDateTime createDateTime;

    @QueryProjection
    public OutMyBookmarkPreviewDto(Long bookmarkId, Long recipeId, String recipeImageName, String recipeName, LocalDateTime createDateTime) {
        this.bookmarkId = bookmarkId;
        this.recipeId = recipeId;
        this.recipeImageName = recipeImageName;
        this.recipeName = recipeName;
        this.createDateTime = createDateTime;
    }

    public MyBookmarkPreviewDto mapping(OutMyBookmarkDtoMapper mapper, ImageUrlConvert imageUrlConvert){
        return mapper.toMyBookmarkPreviewDto(this, imageUrlConvert.getUrl(recipeImageName));
    }
}
