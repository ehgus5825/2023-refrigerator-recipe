package refrigerator.server.security.authorization;

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
import refrigerator.server.security.common.dto.ClaimsDto;
import refrigerator.server.security.authorization.provider.JwtAuthenticationProvider;
import refrigerator.server.security.authorization.provider.JwtAuthenticationToken;
import refrigerator.server.security.authorization.provider.JwtValidationService;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationProviderTest {

    @InjectMocks JwtAuthenticationProvider provider;
    @Mock JwtValidationService service;

    @Test
    @DisplayName("인증 논리 성공 후 인증 객체 생성")
    void authenticate() {
        given(service.checkValidation("token"))
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