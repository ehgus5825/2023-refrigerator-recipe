package refrigerator.back.recipe.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
public class RecipeCourseDto {

    private String step;
    private String explanation;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String courseImage;
}
