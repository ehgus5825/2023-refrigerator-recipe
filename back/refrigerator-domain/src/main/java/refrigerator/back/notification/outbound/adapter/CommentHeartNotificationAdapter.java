package refrigerator.back.notification.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import refrigerator.back.comment.exception.CommentExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;
import refrigerator.back.notification.outbound.mapper.OutNotificationMapper;
import refrigerator.back.notification.outbound.repository.query.NotificationQueryRepository;
import refrigerator.back.notification.application.dto.CommentNotificationDTO;
import refrigerator.back.notification.application.port.out.FindCommentDetailsPort;
import refrigerator.back.notification.application.port.out.FindSenderNicknamePort;
import refrigerator.back.notification.exception.NotificationExceptionType;

@Component
@RequiredArgsConstructor
public class CommentHeartNotificationAdapter implements FindSenderNicknamePort, FindCommentDetailsPort {

    private final NotificationQueryRepository notificationQueryRepository;
    private final OutNotificationMapper mapper;

    @Override
    public String getNickname(String memberId) {
        return notificationQueryRepository.getNickname(memberId)
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER));
    }

    @Override
    public CommentNotificationDTO getDetails(Long commentId) {
        return notificationQueryRepository.getDetails(commentId)
                .orElseThrow(() -> new BusinessException(CommentExceptionType.FAIL_DELETE_COMMENT))
                .mapping(mapper);
    }
}
