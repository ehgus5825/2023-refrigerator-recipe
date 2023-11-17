package refrigerator.back.notification.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentNotificationDTO {
    private String authorId;
    private Long recipeId;
}
