package refrigerator.back.notification.application.port.out;


import refrigerator.back.notification.application.domain.entity.Notification;

public interface SaveNotificationPort {
    Long saveNotification(Notification notification);
}
