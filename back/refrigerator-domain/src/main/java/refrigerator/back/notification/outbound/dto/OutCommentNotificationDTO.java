package refrigerator.back.notification.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.outbound.mapper.OutNotificationMapper;

@Getter
@Builder
public class OutCommentNotificationDTO {

    private String authorId;
    private Long recipeId;

    @QueryProjection
    public OutCommentNotificationDTO(String authorId, Long recipeId) {
        this.authorId = authorId;
        this.recipeId = recipeId;
    }

    public CommentNotificationDTO mapping(OutNotificationMapper mapper){
        return mapper.toCommentNotificationDetail(this);
    }
}