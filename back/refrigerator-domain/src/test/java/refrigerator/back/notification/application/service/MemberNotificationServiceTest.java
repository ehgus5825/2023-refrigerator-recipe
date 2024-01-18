package refrigerator.back.notification.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.notification.application.domain.entity.MemberNotification;
import refrigerator.back.notification.application.port.out.SaveMemberNotificationPort;
import refrigerator.back.notification.application.port.out.FindMemberNotificationPort;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MemberNotificationServiceTest {

    @InjectMocks MemberNotificationService memberNotificationService;
    @Mock SaveMemberNotificationPort saveMemberNotificationPort;
    @Mock FindMemberNotificationPort findMemberNotificationPort;

    @Test
    @DisplayName("알림 아이콘 신호 조회 테스트 (알림 아이콘 빨간점 on/off)")
    void getMemberNotificationSignTest() {

        String memberId = "email123@gmail.com";

        MemberNotification memberNotification = MemberNotification.builder()
                .memberId(memberId)
                .sign(false)
                .build();

        given(findMemberNotificationPort.getMemberNotification(memberId))
                .willReturn(memberNotification);

        assertThat(memberNotificationService.getMemberNotificationSign(memberId).getSign())
                .isEqualTo(false);
    }

    @Test
    @DisplayName("회원 가입시 알림 아이콘 신호 생성 테스트")
    void createMemberNotificationTest() {

        String memberId = "email123@gmail.com";

        given(saveMemberNotificationPort.save(any()))
                .willReturn("1");

        memberNotificationService.createMemberNotification(memberId);

        verify(saveMemberNotificationPort, times(1)).save(any());
    }

    @Test
    @DisplayName("종 버튼 눌려서 빨간점 없애기 테스트")
    void turnOffMemberNotificationTest() {

        String memberId = "email123@gmail.com";

        MemberNotification memberNotification = MemberNotification.builder()
                .memberId(memberId)
                .sign(true)
                .build();

        given(findMemberNotificationPort.getMemberNotification(memberId))
                .willReturn(memberNotification);

        memberNotificationService.turnOffMemberNotification(memberId);

        verify(findMemberNotificationPort, times(1)).getMemberNotification(memberId);
    }
}