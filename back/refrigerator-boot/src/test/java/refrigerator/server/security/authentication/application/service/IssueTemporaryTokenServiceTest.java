package refrigerator.server.security.authentication.application.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import refrigerator.server.security.authentication.application.usecase.IssueTemporaryTokenUseCase;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class IssueTemporaryTokenServiceTest {

    @Autowired IssueTemporaryTokenUseCase issueTemporaryTokenUseCase;

    @Test
    @DisplayName("임시 토큰 발급 성공 테스트")
    void issueTemporaryAccessTokenSuccessTest() {
        String email = "mstest102@gmail.com";
        String temporaryAccessToken = issueTemporaryTokenUseCase.issueTemporaryAccessToken(email);
        assertNotNull(temporaryAccessToken);
    }
}