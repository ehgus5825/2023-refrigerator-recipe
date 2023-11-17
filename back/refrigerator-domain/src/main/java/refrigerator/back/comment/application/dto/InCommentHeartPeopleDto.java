package refrigerator.back.comment.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InCommentHeartPeopleDto {

    private Boolean isLiked;
    private String peopleNo;

    public InCommentHeartPeopleDto(Boolean isLiked, String peopleNo) {
        this.isLiked = isLiked;
        this.peopleNo = peopleNo;
    }
}
