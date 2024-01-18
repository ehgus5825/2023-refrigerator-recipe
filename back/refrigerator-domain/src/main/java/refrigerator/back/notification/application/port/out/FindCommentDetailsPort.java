package refrigerator.back.notification.application.port.out;


import refrigerator.back.notification.application.dto.CommentNotificationDTO;

public interface FindCommentDetailsPort {
    CommentNotificationDTO getDetails(Long commentId);
}
