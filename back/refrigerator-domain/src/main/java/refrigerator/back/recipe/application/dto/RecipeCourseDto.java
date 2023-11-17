package refrigerator.back.recipe.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
public class RecipeCourseDto {

    private String step;
    private String explanation;

    /* DB 데이터가 정제되지 않았기 때문에 과정 사진은 임시 생략 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String courseImage;
}
