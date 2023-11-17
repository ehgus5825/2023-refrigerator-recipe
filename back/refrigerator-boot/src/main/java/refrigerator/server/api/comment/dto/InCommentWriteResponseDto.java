package refrigerator.server.api.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InCommentWriteResponseDto {
    private Long commentId;
}
