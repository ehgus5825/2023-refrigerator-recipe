package refrigerator.back.notification.application.port.out;

public interface UpdateNotificationReadStatusPort {
    void updateReadStatus(Long notificationId, boolean status);
}
