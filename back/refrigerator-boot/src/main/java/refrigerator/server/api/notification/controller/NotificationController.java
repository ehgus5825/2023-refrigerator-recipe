package refrigerator.server.api.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import refrigerator.server.api.authentication.GetMemberEmailUseCase;
import refrigerator.server.api.global.common.BasicListResponseDTO;
import refrigerator.back.notification.application.dto.NotificationDTO;
import refrigerator.back.notification.application.port.in.notification.FindNotificationListUseCase;
import refrigerator.back.notification.application.port.in.notification.ModifyNotificationReadStatusUseCase;


@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final FindNotificationListUseCase findNotificationListUseCase;
    private final ModifyNotificationReadStatusUseCase modifyNotificationReadStatusUseCase;

    private final GetMemberEmailUseCase memberInformation;

    @GetMapping("/api/notifications")
    public BasicListResponseDTO<NotificationDTO> getNotificationList(@RequestParam("page") int page,
                                                                     @RequestParam(value = "size", defaultValue = "10") int size) {

        return new BasicListResponseDTO<>(findNotificationListUseCase
                .getNotificationList(memberInformation.getMemberEmail(), page, size));
    }

    @PutMapping("/api/notifications/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void readNotification(@PathVariable("notificationId") Long notificationId){
        modifyNotificationReadStatusUseCase.modifyNotificationReadStatus(notificationId);
    }

}
