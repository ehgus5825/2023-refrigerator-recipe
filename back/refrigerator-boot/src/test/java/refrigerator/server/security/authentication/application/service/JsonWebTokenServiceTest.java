package refrigerator.server.security.authentication.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import refrigerator.server.security.authentication.application.TokenStatus;
import refrigerator.server.security.authentication.application.ClaimsDto;
import refrigerator.server.security.authentication.application.usecase.JsonWebTokenUseCase;

import java.util.Base64;
import java.util.UUID;

class JsonWebTokenServiceTest {

    private JsonWebTokenUseCase service;

    @BeforeEach
    void init(){
        service = createService(createKey());
    }

    @Test
    @DisplayName("토큰 생성")
    void createToken() {
        // given && when
        String token = service.createToken("username", "authority", 1000 * 60);
        // then
        ClaimsDto result = service.parseClaims(token);
        Assertions.assertEquals("username", result.getEmail());
        Assertions.assertEquals("authority", result.getRole());
        Assertions.assertEquals(service.getTokenStatus(token), TokenStatus.PASS);
    }

    @Test
    @DisplayName("토큰 상태 조회 -> 만료된 토큰")
    void getTokenExpiredStatus() {
        String token = service.createToken("username", "authority", 0);
        Assertions.assertEquals(service.getTokenStatus(token), TokenStatus.EXPIRED);
    }

    @Test
    @DisplayName("토큰 상태 조회 -> 다른 키로 생성된 토큰")
    void getTokenWrongStatus() {
        JsonWebTokenUseCase service1 = createService(createKey());
        JsonWebTokenUseCase service2 = createService(createKey());
        String token = service1.createToken("username", "authority", 1000 * 60);
        Assertions.assertEquals(service2.getTokenStatus(token), TokenStatus.WRONG);
    }

    private JsonWebTokenUseCase createService(String key){
        return new JsonWebTokenService(key);
    }

    private String createKey(){
        String randomText = UUID.randomUUID().toString();
        return Base64.getEncoder().encodeToString((randomText + randomText).getBytes());
    }
}