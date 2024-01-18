package refrigerator.server.api.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentWriteResponseDto {
    private Long commentId;
}
