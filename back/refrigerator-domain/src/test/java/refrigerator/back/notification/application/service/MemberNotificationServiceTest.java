package refrigerator.back.notification.application.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import refrigerator.back.notification.application.dto.NotificationSignDTO;
import refrigerator.back.notification.application.port.out.memberNotification.CreateMemberNotificationPort;
import refrigerator.back.notification.application.port.out.memberNotification.FindMemberNotificationSignPort;
import refrigerator.back.notification.application.port.out.memberNotification.ModifyMemberNotificationPort;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MemberNotificationServiceTest {

    @InjectMocks MemberNotificationService memberNotificationService;
    @Mock CreateMemberNotificationPort createMemberNotificationPort;
    @Mock FindMemberNotificationSignPort findMemberNotificationSignPort;
    @Mock ModifyMemberNotificationPort modifyMemberNotificationPort;

    @Test
    @DisplayName("알림 아이콘 신호 조회 테스트 (종 버튼에 빨간점) : true => O / false => X")
    void getMemberNotificationSignTest_case1() {
        String memberId = "email123@gmail.com";

        given(findMemberNotificationSignPort.getSign(memberId))
                .willReturn(Optional.of(true));

        assertThat(memberNotificationService.getMemberNotificationSign(memberId).getSign())
                .isEqualTo(true);
    }

    @Test
    @DisplayName("알림 아이콘 신호 조회 테스트 (종 버튼에 빨간점) : null => 회원가입시 상황이라고 인식")
    void getMemberNotificationSignTest_case2() {
        String memberId = "email123@gmail.com";

        given(findMemberNotificationSignPort.getSign(memberId))
                .willReturn(Optional.empty());

        given(createMemberNotificationPort.create(memberId))
                .willReturn("1");

        assertThat(memberNotificationService.getMemberNotificationSign(memberId).getSign())
                .isEqualTo(false);

    }

    @Test
    @DisplayName("회원 가입시 redis에 종버튼 Boolean 생성 테스트")
    void createMemberNotificationTest() {
        String memberId = "email123@gmail.com";

        given(createMemberNotificationPort.create(memberId))
                .willReturn("1");

        memberNotificationService.createMemberNotification(memberId);

        verify(createMemberNotificationPort, times(1)).create(memberId);
    }

    @Test
    @DisplayName("종 버튼 눌려서 빨간점 없애기 테스트")
    void turnOffMemberNotificationTest() {
        String memberId = "email123@gmail.com";

        given(modifyMemberNotificationPort.modify(memberId, false))
                .willReturn("1");

        memberNotificationService.turnOffMemberNotification(memberId);

        verify(modifyMemberNotificationPort, times(1)).modify(memberId,false);
    }
}