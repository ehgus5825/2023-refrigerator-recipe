package refrigerator.back.notification.application.port.out.notification;


import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.dto.NotificationDTO;

import java.util.List;

public interface FindNotificationListPort {
    List<Notification> findNotificationList(String email, int page, int size);
}
