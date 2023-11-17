package refrigerator.back.notification.outbound.repository.redis;

import org.springframework.data.repository.CrudRepository;
import refrigerator.back.notification.application.domain.MemberNotification;

import java.util.Optional;

public interface MemberNotificationPersistenceRepository extends CrudRepository<MemberNotification, String> {


    Optional<MemberNotification> findByMemberId(String memberId);
}
