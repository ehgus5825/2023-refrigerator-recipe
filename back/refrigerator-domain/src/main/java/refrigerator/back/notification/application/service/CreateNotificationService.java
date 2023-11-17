package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.port.in.commentHeart.CreateCommentHeartNotificationUseCase;
import refrigerator.back.notification.application.port.out.commentHeart.FindCommentDetailsPort;
import refrigerator.back.notification.application.port.out.commentHeart.FindSenderNicknamePort;
import refrigerator.back.notification.application.port.out.memberNotification.ModifyMemberNotificationPort;
import refrigerator.back.notification.application.port.out.notification.SaveNotificationPort;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CreateNotificationService implements CreateCommentHeartNotificationUseCase {

    private final FindSenderNicknamePort findSenderNicknamePort;
    private final FindCommentDetailsPort commentDetailsPort;
    private final SaveNotificationPort saveNotificationPort;
    private final ModifyMemberNotificationPort modifyMemberNotificationPort;

    private final CurrentTime<LocalDateTime> currentTime;

    // TODO : 알림 생성 Path 수정해야함. (+도메인) => 좀 더 고민
    
    @Override
    public Long createCommentHeartNotification(String senderId, Long commentId) {
        
        String senderNickname = findSenderNicknamePort.getNickname(senderId);
        CommentNotificationDTO details = commentDetailsPort.getDetails(commentId);

        Notification notification = madeNotification(commentId, details);

        notification.createCommentHeartMessage(senderNickname);
        
        modifyMemberNotificationPort.modify(details.getAuthorId(), true);
        
        return saveNotificationPort.saveNotification(notification);
    }

    public Notification madeNotification(Long commentId, CommentNotificationDTO details) {

        Notification notification = Notification.create(
                NotificationType.HEART,
                "/recipe/comment?recipeId=" + details.getRecipeId() + "&commentID=" + commentId,
                details.getAuthorId(),
                BasicHttpMethod.GET.name(),
                currentTime.now()
        );
        return notification;
    }
}