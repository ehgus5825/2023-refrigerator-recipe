package refrigerator.server.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.application.dto.UserDto;
import refrigerator.back.authentication.application.port.out.GetMemberCredentialsPort;
import refrigerator.back.global.exception.BasicException;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.exception.MemberExceptionType;

import java.util.Map;

@Component
public class DefaultOAuth2UserDetailsHandler implements OAuth2UserDetailsHandler {

    private final GetMemberCredentialsPort getMemberCredentialsPort;
    private final PasswordEncoder passwordEncoder;
    private final JoinUseCase joinUseCase;
    private final String oauthPassword;

    public DefaultOAuth2UserDetailsHandler(GetMemberCredentialsPort getMemberCredentialsPort,
                                           PasswordEncoder passwordEncoder,
                                           JoinUseCase joinUseCase,
                                           @Value("${oauth.password}") String oauthPassword) {
        this.getMemberCredentialsPort = getMemberCredentialsPort;
        this.passwordEncoder = passwordEncoder;
        this.joinUseCase = joinUseCase;
        this.oauthPassword = oauthPassword;
    }

    @Override
    public OAuth2User get(String providerId, Map<String, Object> attributes) {
        LoginMethod loginMethod = LoginMethod.findByProviderId(providerId);
        String email = loginMethod.getEmail(attributes);
        String nickname = loginMethod.getNickname(attributes);
        try{
            UserDto user = getMemberCredentialsPort.getUser(email);
            return createUser(attributes, email, user.getStatus());
        } catch (BusinessException e){
            if (e.getBasicExceptionType().equals(MemberExceptionType.NOT_FOUND_MEMBER)){
                joinUseCase.join(email, passwordEncoder.encode(oauthPassword), nickname);
                return createUser(attributes, email, MemberStatus.STEADY_STATUS.getStatusCode());
            }
            throw e;
        }
    }

    private OAuthUser createUser(Map<String, Object> attributes, String email, String authority) {
        return OAuthUser.builder()
                .username(email)
                .password(oauthPassword)
                .attributes(attributes)
                .authority(authority).build();
    }
}
