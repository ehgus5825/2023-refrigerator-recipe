package refrigerator.server.security.authentication.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.server.api.authentication.application.usecase.IssueTemporaryTokenUseCase;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class IssueTemporaryTokenServiceTest {

    @Autowired IssueTemporaryTokenUseCase issueTemporaryTokenUseCase;

    @Autowired JoinUseCase joinUseCase;

    @Autowired PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("임시 토큰 발급 성공 테스트")
    void issueTemporaryAccessTokenSuccessTest() {

        joinUseCase.join("dhtest102@gmail.com", passwordEncoder.encode("password123!"), "nick");

        String email = "dhtest102@gmail.com";
        String temporaryAccessToken = issueTemporaryTokenUseCase.issueTemporaryAccessToken(email);
        assertNotNull(temporaryAccessToken);
    }
}