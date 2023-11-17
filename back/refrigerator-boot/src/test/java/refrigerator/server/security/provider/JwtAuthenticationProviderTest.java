package refrigerator.server.security.provider;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import refrigerator.server.security.authentication.application.ClaimsDto;
import refrigerator.server.security.token.JwtAuthenticationToken;
import refrigerator.server.security.authentication.application.service.JwtValidationService;

@ExtendWith(MockitoExtension.class)
@Slf4j
class JwtAuthenticationProviderTest {

    @InjectMocks
    JwtAuthenticationProvider provider;
    @Mock
    JwtValidationService service;

    @Test
    @DisplayName("인증 논리 성공 후 인증 객체 생성")
    void authenticate() {
        BDDMockito.given(service.checkValidation("token"))
                .willReturn(new ClaimsDto("email", "role", null));

        /* JwtAuthenticationFilter 에서 넘어온 Authentication 객체로 provider 인증 진행 */
        Authentication fromFilter = new JwtAuthenticationToken("token");
        Assertions.assertFalse(fromFilter.isAuthenticated());

        Authentication result = provider.authenticate(fromFilter);

        Assertions.assertTrue(result.isAuthenticated());
        Assertions.assertEquals("email", result.getName());
    }

    @Test
    @DisplayName("인증 논리 실패 후 null 반환")
    void authenticateFailTest() {
        /* JwtAuthenticationFilter 에서 넘어온 Authentication 객체로 provider 인증 진행 */
        Authentication fromFilter = new JwtAuthenticationToken(null);
        Assertions.assertFalse(fromFilter.isAuthenticated());

        Authentication result = provider.authenticate(fromFilter);
        Assertions.assertNull(result);
    }

    @Test
    @DisplayName("provider 인증 객체 클래스 타입 확인")
    void supports() {
        Authentication fromFilter = new JwtAuthenticationToken("token");
        Assertions.assertTrue(provider.supports(fromFilter.getClass()));
    }
}