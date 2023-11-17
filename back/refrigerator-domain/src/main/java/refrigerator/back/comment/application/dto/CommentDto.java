package refrigerator.back.comment.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDto {
    private Long commentId;
    private String nickname;
    private Integer heart;
    private String createDate;
    private Boolean modifiedState;
    private String content;
    private Boolean likedState;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CommentHeartPeopleDto likedInfo;

    @Builder
    public CommentDto(Long commentId, String nickname, Integer heart, String createDate, Boolean modifiedState, String content, CommentHeartPeopleDto likedInfo) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.heart = heart;
        this.createDate = createDate;
        this.modifiedState = modifiedState;
        this.content = content;
        this.likedState = false;
        this.likedInfo = likedInfo;
    }

    public void isLiked(Map<Long, CommentHeartPeopleDto> peoples){
        CommentHeartPeopleDto people = peoples.getOrDefault(commentId, null);
        if (people != null && people.getCommentId().equals(commentId)){
            likedState = true;
            likedInfo = people;
        }
    }
}
