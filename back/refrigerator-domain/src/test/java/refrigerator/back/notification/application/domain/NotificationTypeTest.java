package refrigerator.back.notification.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.notification.application.domain.value.NotificationType;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationTypeTest {
    
    @Test
    @DisplayName("알림 enum 타입 테스트")
    void notificationTypeTest() {
        assertThat(NotificationType.HEART.getTypeName()).isEqualTo("좋아요");
        assertThat(NotificationType.ONE_DAY_BEFORE_EXPIRATION.getTypeName()).isEqualTo("유통기한 1일 전");
        assertThat(NotificationType.THREE_DAY_BEFORE_EXPIRATION.getTypeName()).isEqualTo("유통기한 3일 전");
        assertThat(NotificationType.NOTICE.getTypeName()).isEqualTo("공지사항");
        assertThat(NotificationType.INGREDIENT.getTypeName()).isEqualTo("식재료");
    }
}