package refrigerator.back.mybookmark.application.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class MyBookmarkPreviewDto {
    private Long bookmarkId;
    private Long recipeId;
    private String recipeImage;
    private String recipeName;
}
