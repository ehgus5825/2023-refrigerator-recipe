package refrigerator.back.notification.application.port.in.commentHeart;

public interface CreateCommentHeartNotificationUseCase {
    Long createCommentHeartNotification(String senderId, Long commentId);
}
