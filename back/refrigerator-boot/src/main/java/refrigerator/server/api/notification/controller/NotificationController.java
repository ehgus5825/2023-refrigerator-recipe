package refrigerator.server.api.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.api.notification.dto.NotificationResponseDto;
import refrigerator.server.security.common.email.GetMemberEmailUseCase;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.port.in.FindNotificationListUseCase;
import refrigerator.back.notification.application.port.in.ModifyNotificationReadStatusUseCase;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class NotificationController {

    private final FindNotificationListUseCase findNotificationListUseCase;
    private final ModifyNotificationReadStatusUseCase modifyNotificationReadStatusUseCase;

    private final GetMemberEmailUseCase memberInformation;

    @GetMapping("/api/notifications")
    public BasicListResponseDTO<NotificationDTO> getNotificationList(
             @RequestParam(value = "page", defaultValue = "0") @PositiveOrZero Integer page,
             @RequestParam(value = "size", defaultValue = "10") @Positive Integer size) {

        return new BasicListResponseDTO<>(findNotificationListUseCase
                .getNotificationList(memberInformation.getMemberEmail(), page, size));
    }

    @GetMapping("/api/notifications/expiration")
    public BasicListResponseDTO<NotificationDTO> getExpirationNotificationList() {

        return new BasicListResponseDTO<>(findNotificationListUseCase
                .getExpirationNotificationList(memberInformation.getMemberEmail()));
    }

    @PutMapping("/api/notifications/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void readNotification(
             @PathVariable("notificationId") @Positive Long notificationId){

        modifyNotificationReadStatusUseCase.modifyNotificationReadStatus(notificationId);
    }

}
