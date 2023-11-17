package refrigerator.server.api.notification.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.port.out.notification.SaveNotificationPort;
import refrigerator.server.config.TestTokenService;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NotificationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    SaveNotificationPort saveNotificationPort;

    @Autowired
    JsonWebTokenUseCase jsonWebTokenUseCase;

    @Test
    @DisplayName("알림 목록 조회")
    void getNotificationListTest() throws Exception {

        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        Notification.NotificationBuilder builder = Notification.builder()
                .type(NotificationType.HEART)
                .path("/")
                .readStatus(false)
                .createDate(now)
                .memberId("mstest102@gmail.com")
                .method(BasicHttpMethod.GET.name());


        saveNotificationPort.saveNotification(builder.message("test message1").build());
        saveNotificationPort.saveNotification(builder.message("test message2").build());
        saveNotificationPort.saveNotification(builder.message("test message3").build());

        mockMvc.perform(
                get("/api/notifications?page=0")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.data").isArray()
        ).andDo(print());
    }

    @Test
    @DisplayName("알림 단건 조회 => 읽음 상태 true로 변경")
    void readNotificationTest() throws Exception {

        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);

        Notification notification = Notification.builder()
                .message("test message")
                .type(NotificationType.HEART)
                .path("/")
                .readStatus(false)
                .createDate(now)
                .memberId("mstest102@gmail.com")
                .method(BasicHttpMethod.GET.name())
                .build();

        Long id = saveNotificationPort.saveNotification(notification);

        mockMvc.perform(
                put("/api/notifications/" + id)
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

}