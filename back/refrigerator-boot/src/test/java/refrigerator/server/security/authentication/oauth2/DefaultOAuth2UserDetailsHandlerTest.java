package refrigerator.server.security.oauth2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import refrigerator.back.authentication.application.dto.UserDto;
import refrigerator.back.authentication.application.port.out.GetMemberCredentialsPort;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.server.security.authentication.application.oauth2.service.DefaultOAuth2UserDetailsHandler;
import refrigerator.server.security.authentication.application.oauth2.service.OAuth2UserDetailsHandler;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultOAuth2UserDetailsHandlerTest {

    OAuth2UserDetailsHandler service;
    GetMemberCredentialsPort getMemberPort;
    JoinUseCase joinUseCase;

    public DefaultOAuth2UserDetailsHandlerTest(@Mock GetMemberCredentialsPort getMemberPort,
                                               @Mock PasswordEncoder passwordEncoder,
                                               @Mock JoinUseCase joinUseCase) {
        this.getMemberPort = getMemberPort;
        this.joinUseCase = joinUseCase;
        this.service = new DefaultOAuth2UserDetailsHandler(getMemberPort, passwordEncoder, joinUseCase, "oauthPassword");
    }

    @Test
    @DisplayName("네이버 간편 로그인을 통해 가져온 회원 정보 가공")
    void getSuccessTest() {
        // given
        given(getMemberPort.getUser("username"))
                .willReturn(new UserDto("username", "oauthPassword", "status"));

        given(joinUseCase.join("username", "oauthPassword", "nickname"))
                .willReturn(1L);

        Map<String, Object> attributes = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        response.put("email", "username");
        response.put("nickname", "nickname");
        attributes.put("response", response);
        // when
        OAuth2User result = service.get("naver", attributes);
        String authority = result.getAuthorities().toArray()[0].toString();
        // then
        Assertions.assertEquals("username", result.getName());
        Assertions.assertEquals("status", authority);
    }
}