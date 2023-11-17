package refrigerator.back.notification.outbound.repository.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.outbound.repository.jpa.NotificationPersistenceRepository;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, NotificationBatchQueryRepository.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotificationBatchQueryRepositoryTest {

    @Autowired TestEntityManager em;

    @Autowired NotificationBatchQueryRepository notificationBatchQueryRepository;

    @Autowired
    NotificationPersistenceRepository notificationPersistenceRepository;

    @BeforeEach
    void setUp() {
        Notification.NotificationBuilder builder = Notification.builder()
                .message("test message")
                .path("/")
                .readStatus(false)
                .memberId("email123@gmail.com")
                .method(BasicHttpMethod.GET.name())
                .createDate(LocalDateTime.of(2023, 1, 1, 0, 0, 0));

        for (NotificationType value : NotificationType.values()) {
            for (int i = 0; i < 4; i++) {
                em.persist(builder.type(value).build());
            }
        }
    }

    @Test
    @DisplayName("알림 삭제 (type이 유통기한 O)")
    void deleteNotificationTest() {

        // given    // 2023.01.02 이전에 있는 알림은 모두 삭제
        LocalDateTime date = LocalDateTime.of(2023,1,2,0,0,0);

        // when
        Long execute = notificationBatchQueryRepository.deleteNotification(true, date);

        // then
        assertThat(execute).isEqualTo(8);
        assertThat(notificationPersistenceRepository.findAll().size()).isEqualTo(12);
    }

    @Test
    @DisplayName("알림 삭제 (type이 유통기한 X)")
    void deleteDeadlineNotificationTest() {

        // given    // 2023.01.02 이전에 있는 알림은 모두 삭제
        LocalDateTime date = LocalDateTime.of(2023,1,2,0,0,0);

        // when
        Long execute = notificationBatchQueryRepository.deleteNotification(false, date);

        // then
        assertThat(execute).isEqualTo(12);
        assertThat(notificationPersistenceRepository.findAll().size()).isEqualTo(8);
    }

    @Test
    @DisplayName("배치 테스트용 알림 삭제 테스트")
    void deleteTestNotificationTest() {

        // given    // 2022.12.31 이후에 있는 알림은 모두 삭제
        LocalDateTime date = LocalDateTime.of(2022,12,31,0,0,0);

        // when
        Long execute = notificationBatchQueryRepository.deleteTestNotification(date);

        // then
        assertThat(execute).isEqualTo(20);
        assertThat(notificationPersistenceRepository.findAll().size())
                .isEqualTo(0);
    }

    @Test
    @DisplayName("createDateLtCheck 테스트")
    void createDateLtCheckTest() {
        assertThatThrownBy(() -> notificationBatchQueryRepository.createDateLtCheck(null))
                .isInstanceOf(BusinessException.class);
    } 

    @Test
    @DisplayName("createDateGoeCheck 테스트")
    void createDateGoeCheckTest() {
        assertThatThrownBy(() -> notificationBatchQueryRepository.createDateGoeCheck(null))
                .isInstanceOf(BusinessException.class);
    }
}