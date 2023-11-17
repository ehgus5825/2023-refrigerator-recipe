package refrigerator.back.notification.application.port.in.notification;

import refrigerator.back.notification.application.dto.NotificationDTO;

import java.util.List;

public interface FindNotificationListUseCase {
    List<NotificationDTO> getNotificationList(String email, int page, int size);
}
