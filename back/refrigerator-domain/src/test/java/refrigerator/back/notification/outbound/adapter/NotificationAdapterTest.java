package refrigerator.back.notification.outbound.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import refrigerator.back.global.jpa.config.QuerydslConfig;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.notification.outbound.repository.query.NotificationQueryRepository;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({QuerydslConfig.class, NotificationQueryRepository.class, NotificationAdapter.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotificationAdapterTest {

    @Autowired NotificationAdapter notificationAdapter;

    @Test
    @DisplayName("알림 단건 조회 (읽음 상태 변경) => 예외 확인")
    void updateReadStatusTest() {
        assertThatThrownBy(() -> notificationAdapter.updateReadStatus(-1L, true))
                .isInstanceOf(BusinessException.class);
    }
}