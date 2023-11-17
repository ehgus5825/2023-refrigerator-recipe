package refrigerator.back.notification.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.outbound.mapper.OutNotificationMapper;
import refrigerator.back.notification.outbound.repository.query.NotificationQueryRepository;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.application.port.out.commentHeart.FindCommentDetailsPort;
import refrigerator.back.notification.application.port.out.commentHeart.FindSenderNicknamePort;
import refrigerator.back.notification.exception.NotificationExceptionType;

@Component
@RequiredArgsConstructor
public class CommentHeartNotificationAdapter implements FindSenderNicknamePort, FindCommentDetailsPort {

    private final NotificationQueryRepository notificationQueryRepository;
    private final OutNotificationMapper mapper;

    @Override
    public String getNickname(String memberId) {
        return notificationQueryRepository.getNickname(memberId)
                .orElseThrow(() -> new BusinessException(NotificationExceptionType.TEST_ERROR));
    }

    @Override
    public CommentNotificationDTO getDetails(Long commentId) {
        return mapper.toCommentNotificationDetail(
                notificationQueryRepository.getDetails(commentId)
                .orElseThrow(() -> new BusinessException(NotificationExceptionType.TEST_ERROR)));
    }
}
