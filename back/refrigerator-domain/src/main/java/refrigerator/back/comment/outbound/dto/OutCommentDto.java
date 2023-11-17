package refrigerator.back.comment.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import refrigerator.back.comment.application.service.CommentTimeService;
import refrigerator.back.comment.outbound.mapper.OutCommentMapper;
import refrigerator.back.comment.application.dto.CommentDto;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
public class OutCommentDto {
    private Long commentId;
    private String nickname;
    private Integer heart;
    private LocalDateTime createDate;
    private Boolean modifiedState;
    private String content;

    @QueryProjection
    public OutCommentDto(Long commentId, String nickname, Integer heart, LocalDateTime createDate, Boolean modifiedState, String content) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.heart = heart;
        this.createDate = createDate;
        this.modifiedState = modifiedState;
        this.content = content;
    }

    public CommentDto mapping(OutCommentMapper mapper, CommentTimeService timeHandler){
        return mapper.toCommentDto(this, timeHandler.replace(createDate));
    }

}
