package refrigerator.back.notification.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.global.time.CurrentTime;
import refrigerator.back.notification.application.domain.entity.MemberNotification;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.application.domain.entity.Notification;
import refrigerator.back.notification.application.domain.value.NotificationType;
import refrigerator.back.notification.application.port.in.CreateCommentHeartNotificationUseCase;
import refrigerator.back.notification.application.port.out.FindCommentDetailsPort;
import refrigerator.back.notification.application.port.out.FindSenderNicknamePort;
import refrigerator.back.notification.application.port.out.FindMemberNotificationPort;
import refrigerator.back.notification.application.port.out.SaveMemberNotificationPort;
import refrigerator.back.notification.application.port.out.SaveNotificationPort;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
public class CreateNotificationService implements CreateCommentHeartNotificationUseCase {

    private final FindSenderNicknamePort findSenderNicknamePort;
    private final FindCommentDetailsPort commentDetailsPort;
    private final SaveNotificationPort saveNotificationPort;
    private final SaveMemberNotificationPort saveMemberNotificationPort;
    private final FindMemberNotificationPort findMemberNotificationPort;

    private final CurrentTime<LocalDateTime> currentTime;
    
    @Override
    public Long createCommentHeartNotification(String senderId, Long commentId) {
        
        String senderNickname = findSenderNicknamePort.getNickname(senderId);
        CommentNotificationDTO details = commentDetailsPort.getDetails(commentId);

        turnOnMemberNotification(details.getAuthorId());

        Notification notification = madeNotification(details, senderNickname);
        
        return saveNotificationPort.saveNotification(notification);
    }

    public Notification madeNotification(CommentNotificationDTO details, String senderNickname) {

        Notification notification = Notification.create(
                NotificationType.HEART,
                "/recipe/comment?recipeID=" + details.getRecipeId() + "&recipeName=" + details.getRecipeName(),
                details.getAuthorId(),
                BasicHttpMethod.GET.name(),
                currentTime.now()
        );

        notification.createCommentHeartMessage(senderNickname);

        return notification;
    }

    public void turnOnMemberNotification(String memberId){

        MemberNotification memberNotification = findMemberNotificationPort
                .getMemberNotification(memberId);

        memberNotification.setSign(true);

        saveMemberNotificationPort.save(memberNotification);
    }
}