package refrigerator.back.notification.application.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.back.notification.application.domain.entity.MemberNotification;

import static org.assertj.core.api.Assertions.*;

class MemberNotificationTest {

    @Test
    @DisplayName("멤버 알림 도메인 테스트")
    void memberNotificationTest() {

        MemberNotification memberNotification = MemberNotification.builder()
                .id(1L)
                .email("email123@gmail.com")
                .sign(false)
                .build();

        assertThat(memberNotification.getId()).isEqualTo(1L);
        assertThat(memberNotification.getEmail()).isEqualTo("email123@gmail.com");
        assertThat(memberNotification.getSign()).isEqualTo(false);

        memberNotification.setSign(true);

        assertThat(memberNotification.getSign()).isEqualTo(true);
    }
}