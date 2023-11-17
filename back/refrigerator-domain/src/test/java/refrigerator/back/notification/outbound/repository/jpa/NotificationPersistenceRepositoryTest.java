package refrigerator.back.notification.outbound.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotificationPersistenceRepositoryTest {

    @Autowired TestEntityManager em;

    @Autowired
    NotificationPersistenceRepository notificationPersistenceRepository;

    @Test
    @DisplayName("알림 생성 테스트")
    void saveNotificationTest() {

        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        Notification notification = Notification.builder()
                .message("test message")
                .type(NotificationType.HEART)
                .path("/")
                .readStatus(false)
                .memberId("email123@gmail.com")
                .createDate(now)
                .method(BasicHttpMethod.GET.name())
                .build();

        Long id = notificationPersistenceRepository.save(notification).getId();

        Notification findNotification = em.find(Notification.class, id);
        assertThat(findNotification.getMessage()).isEqualTo("test message");
        assertThat(findNotification.getType()).isEqualTo(NotificationType.HEART);
        assertThat(findNotification.getPath()).isEqualTo("/");
        assertThat(findNotification.isReadStatus()).isEqualTo(false);
        assertThat(findNotification.getMemberId()).isEqualTo("email123@gmail.com");
        assertThat(findNotification.getCreateDate()).isEqualTo(now);
        assertThat(findNotification.getMethod()).isEqualTo(BasicHttpMethod.GET.name());
    }

}