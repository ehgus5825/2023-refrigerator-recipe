package refrigerator.back.myscore.application.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class MyScoreDetailDto {

    private Long scoreId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;
    private Double myScore;

}