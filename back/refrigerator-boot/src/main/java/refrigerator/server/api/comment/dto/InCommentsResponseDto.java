package refrigerator.server.api.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.comment.application.dto.CommentDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class InCommentsResponseDto {

    private List<CommentDto> comments;
    private List<CommentDto> myComments;

    public InCommentsResponseDto(List<CommentDto> comments, List<CommentDto> myComments) {
        this.comments = comments;
        this.myComments = myComments;
    }
}
