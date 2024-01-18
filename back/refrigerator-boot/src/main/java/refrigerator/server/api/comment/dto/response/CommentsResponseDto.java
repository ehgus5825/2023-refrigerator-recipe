package refrigerator.server.api.comment.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.comment.application.dto.CommentDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommentsResponseDto {

    private List<CommentDto> comments;
    private List<CommentDto> myComments;

    public CommentsResponseDto(List<CommentDto> comments, List<CommentDto> myComments) {
        this.comments = comments;
        this.myComments = myComments;
    }
}
