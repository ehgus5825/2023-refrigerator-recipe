package refrigerator.back.mybookmark.application.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InMyBookmarkPreviewsDto {
    private List<MyBookmarkPreviewDto> bookmarks;
    private Integer count;
}
