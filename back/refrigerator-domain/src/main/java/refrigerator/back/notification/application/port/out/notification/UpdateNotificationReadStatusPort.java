package refrigerator.back.notification.application.port.out.notification;

public interface UpdateNotificationReadStatusPort {
    void updateReadStatus(Long notificationId, boolean status);
}
