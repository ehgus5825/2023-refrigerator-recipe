package refrigerator.back.notification.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberNotificationTest {

    @Test
    @DisplayName("멤버 알림 도메인 테스트")
    void memberNotificationTest() {

        MemberNotification memberNotification = MemberNotification.builder()
                .id("1")
                .memberId("email123@gmail.com")
                .sign(false)
                .build();

        assertThat(memberNotification.getId()).isEqualTo("1");
        assertThat(memberNotification.getMemberId()).isEqualTo("email123@gmail.com");
        assertThat(memberNotification.getSign()).isEqualTo(false);

        memberNotification.setSign(true);

        assertThat(memberNotification.getSign()).isEqualTo(true);
    }
}