package refrigerator.back.comment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentHeartPeopleDto {

    private String peopleId;
    private Long commentId;

}
