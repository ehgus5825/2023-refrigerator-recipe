package refrigerator.back.notification.application.port.out;


import refrigerator.back.notification.application.dto.NotificationDTO;

import java.util.List;

public interface FindNotificationListPort {
    List<NotificationDTO> findNotificationList(String email, int page, int size);

    List<NotificationDTO> findExpirationNotificationList(String email);
}
