package refrigerator.server.api.notification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.back.notification.application.dto.NotificationDTO;

import java.util.List;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {

    private List<NotificationDTO> comments;
    private List<NotificationDTO> expComments;

    public NotificationResponseDto(List<NotificationDTO> comments, List<NotificationDTO> expComments) {
        this.comments = comments;
        this.expComments = expComments;
    }
}
