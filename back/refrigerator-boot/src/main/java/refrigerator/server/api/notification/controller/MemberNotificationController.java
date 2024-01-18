package refrigerator.server.api.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.back.notification.application.dto.NotificationSignDTO;
import refrigerator.back.notification.application.port.in.FindMemberNotificationSignUseCase;
import refrigerator.back.notification.application.port.in.TurnOffMemberNotificationSignUseCase;

@RestController
@RequiredArgsConstructor
public class MemberNotificationController {

    private final FindMemberNotificationSignUseCase findMemberNotificationSignUseCase;
    private final TurnOffMemberNotificationSignUseCase turnOffMemberNotificationSignUseCase;

    private final GetMemberEmailUseCase memberInformation;

    @GetMapping("/api/notifications/sign")
    public NotificationSignDTO getNotificationSign(){

        return findMemberNotificationSignUseCase.getMemberNotificationSign(memberInformation.getMemberEmail());
    }

    @PutMapping("/api/notifications/sign/off")       // turn off sign
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void TurnOffNotificationSign(){

        turnOffMemberNotificationSignUseCase.turnOffMemberNotification(memberInformation.getMemberEmail());
    }
}
