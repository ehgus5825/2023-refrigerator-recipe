package refrigerator.back.mybookmark.application.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class MyBookmarkDto {
    private Long bookmarkId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;
    private Double scoreAvg;
    private Integer views;
}
