package refrigerator.back.notification.outbound.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import refrigerator.back.notification.application.domain.value.NotificationType;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.service.NotificationTimeService;
import refrigerator.back.notification.outbound.mapper.OutNotificationMapper;

import java.time.LocalDateTime;

@Getter
@Builder
public class OutNotificationDTO {

    private Long id;
    private String message;
    private NotificationType type;
    private LocalDateTime createDate;
    private String path;
    private boolean readStatus;

    @QueryProjection
    public OutNotificationDTO(Long id, String message, NotificationType type, LocalDateTime createDate, String path, boolean readStatus) {
        this.id = id;
        this.message = message;
        this.type = type;
        this.createDate = createDate;
        this.path = path;
        this.readStatus = readStatus;
    }

    public NotificationDTO mapping(OutNotificationMapper mapper, NotificationTimeService timeService){
        return mapper.toNotificationDTO(this, timeService.replace(this.createDate));
    }
}