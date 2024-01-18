package refrigerator.back.notification.application.port.in;

public interface CreateCommentHeartNotificationUseCase {
    Long createCommentHeartNotification(String senderId, Long commentId);
}
