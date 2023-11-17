package refrigerator.back.notification.application.port.out.notification;


import refrigerator.back.notification.application.domain.Notification;

public interface SaveNotificationPort {
    Long saveNotification(Notification notification);
}
