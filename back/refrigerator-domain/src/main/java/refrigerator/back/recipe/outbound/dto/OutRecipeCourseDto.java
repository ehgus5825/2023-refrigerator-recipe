package refrigerator.back.recipe.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import refrigerator.back.global.s3.ImageUrlConvert;
import refrigerator.back.recipe.application.dto.RecipeCourseDto;
import refrigerator.back.recipe.outbound.mapper.OutRecipeDtoMapper;

@Getter
@Builder
public class OutRecipeCourseDto {

    private Long courseId;
    private Integer step;
    private String explanation;
    private String imageName;

    @QueryProjection
    public OutRecipeCourseDto(Long courseId, Integer step, String explanation, String imageName) {
        this.courseId = courseId;
        this.step = step;
        this.explanation = explanation;
        this.imageName = imageName;
    }

    public RecipeCourseDto mapping(OutRecipeDtoMapper mapper, ImageUrlConvert imageUrlConvert){
        return mapper.toRecipeCourseDto(this, null);
    }
}
