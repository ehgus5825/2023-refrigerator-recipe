package refrigerator.back.notification.outbound.repository.redis;

import org.springframework.data.jpa.repository.JpaRepository;
import refrigerator.back.notification.application.domain.entity.MemberNotification;

import java.util.Optional;

public interface MemberNotificationPersistenceRepository extends JpaRepository<MemberNotification, String> {

    Optional<MemberNotification> findByEmail(String memberId);
}
