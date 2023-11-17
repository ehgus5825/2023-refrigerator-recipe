package refrigerator.server.security.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import refrigerator.back.authentication.application.dto.UserDto;
import refrigerator.back.authentication.application.port.out.GetMemberCredentialsPort;
import refrigerator.back.global.exception.BasicException;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.exception.MemberExceptionType;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2DetailsServiceImpl extends DefaultOAuth2UserService {

    private final OAuth2UserDetailsHandler oAuth2UserDetailsHandler;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String providerId = userRequest.getClientRegistration().getRegistrationId();
        return oAuth2UserDetailsHandler.get(providerId, oAuth2User.getAttributes());
    }
}
