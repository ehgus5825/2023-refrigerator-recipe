package refrigerator.server.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import refrigerator.server.security.exception.CustomAuthenticationException;
import refrigerator.server.security.exception.LoginMethodExceptionType;

import java.util.Arrays;
import java.util.Map;

@AllArgsConstructor
@Slf4j
public enum LoginMethod {

    NAVER("naver",
            "email",
            "nickname",
            ((attributes, targetKey) -> {
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                if (!response.containsKey(targetKey)){
                    throw new CustomAuthenticationException(LoginMethodExceptionType.EMPTY_NAVER_LOGIN_ATTRIBUTES);
                }
                return response.get(targetKey).toString();
            })),

    GOOGLE("google",
            "email",
            "name",
            (((attributes, targetKey) -> attributes.get(targetKey).toString())))
    ;

    @Getter
    private final String providerId;
    private final String emailKey;
    private final String nicknameKey;
    private final LoginMethodCall<String> parsingMethod;

    public static LoginMethod findByProviderId(String providerId){
        return Arrays.stream(LoginMethod.values())
                .filter(loginMethod -> loginMethod.getProviderId().equals(providerId))
                .findAny()
                .orElseThrow(() -> new OAuth2AuthenticationException(
                        new OAuth2Error("NOT_FOUND_OAUTH2_EMAIL", "회원의 정보를 가져오지 못했습니다", "")
                ));
    }

    public String getEmail(Map<String, Object> attributes){
        return parsingMethod.call(attributes, emailKey);
    }

    public String getNickname(Map<String, Object> attributes){
        return parsingMethod.call(attributes, nicknameKey);
    }

}
