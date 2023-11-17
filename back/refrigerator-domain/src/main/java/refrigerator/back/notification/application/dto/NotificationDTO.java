package refrigerator.back.notification.application.dto;

import lombok.*;
import refrigerator.back.notification.application.domain.NotificationType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationDTO {

    private Long id;
    private String message;
    private NotificationType type;
    private String registerTime;
    private String path;
    private boolean readStatus;
}
