package refrigerator.back.comment.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InCommentsPreviewResponseDto {

    private List<CommentDto> comments;
    private Integer count;

    public InCommentsPreviewResponseDto(List<CommentDto> comments, Integer count) {
        this.comments = comments;
        this.count = count;
    }
}
