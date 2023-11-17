package refrigerator.server.api.notification.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.global.exception.BasicHttpMethod;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.notification.application.domain.Notification;
import refrigerator.back.notification.application.domain.NotificationType;
import refrigerator.back.notification.application.port.out.memberNotification.CreateMemberNotificationPort;
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
class MemberNotificationControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonWebTokenUseCase jsonWebTokenUseCase;

    @Autowired
    CreateMemberNotificationPort createMemberNotificationPort;

    @Test
    @DisplayName("알림 신호 조회 => 종 버튼에 빨간점이 있는지 없는지 확인")
    void getNotificationSignTest() throws Exception {

        mockMvc.perform(
                get("/api/notifications/sign")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andExpect(jsonPath("$.sign").isBoolean()
        ).andDo(print());
    }

    @Test
    @DisplayName("알림 신호 끄기 => 종 버튼 눌러서 빨간 점 삭제")
    void TurnOffNotificationSignTest() throws Exception {

        createMemberNotificationPort.create("mstest102@gmail.com");

        mockMvc.perform(
                put("/api/notifications/sign/off")
                        .header(HttpHeaders.AUTHORIZATION, TestTokenService.getToken(jsonWebTokenUseCase))
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}