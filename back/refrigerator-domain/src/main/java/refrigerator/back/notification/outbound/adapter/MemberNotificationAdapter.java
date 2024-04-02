package refrigerator.back.notification.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.outbound.repository.redis.MemberNotificationPersistenceRepository;
import refrigerator.back.notification.application.domain.entity.MemberNotification;
import refrigerator.back.notification.application.port.out.FindMemberNotificationPort;
import refrigerator.back.notification.application.port.out.SaveMemberNotificationPort;
import refrigerator.back.notification.exception.NotificationExceptionType;


@Repository
@RequiredArgsConstructor
public class MemberNotificationAdapter implements SaveMemberNotificationPort, FindMemberNotificationPort {

    private final MemberNotificationPersistenceRepository repository;

    @Override
    public Long save(MemberNotification memberNotification) {
        return repository.save(memberNotification).getId();
    }


    @Override
    public MemberNotification getMemberNotification(String memberId) {
        return repository.findByEmail(memberId)
                .orElseThrow(() -> new BusinessException(NotificationExceptionType.NOT_FOUND_MEMBER_NOTIFICATION));
    }
}