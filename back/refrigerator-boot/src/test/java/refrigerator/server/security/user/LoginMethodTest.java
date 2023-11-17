package refrigerator.server.security.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import refrigerator.server.security.exception.CustomAuthenticationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class LoginMethodTest {

    @Test
    @DisplayName("구글 provideId 로 LoginMethod 객체 찾기")
    void findByGoogleProviderId() {
        LoginMethod google = LoginMethod.findByProviderId("google");
        OAuth2User user = new GoogleUser();
        String email = google.getEmail(user.getAttributes());
        String nickname = google.getNickname(user.getAttributes());
        Assertions.assertEquals(email, "googleEmail");
        Assertions.assertEquals(nickname, "googleNickname");
    }

    @Test
    @DisplayName("네이버 provideId 로 LoginMethod 객체 찾기")
    void findByNaverProviderId() {
        LoginMethod naver = LoginMethod.findByProviderId("naver");
        OAuth2User user = new NaverUser();
        String email = naver.getEmail(user.getAttributes());
        String nickname = naver.getNickname(user.getAttributes());
        Assertions.assertEquals(email, "naverEmail");
        Assertions.assertEquals(nickname, "naverNickname");
    }

    @Test
    @DisplayName("닉네임 사용자 email 파싱 실패 -> attribute 가 비어있을 때")
    void findByGoogleProviderIdFailTest1() {
        LoginMethod naver = LoginMethod.findByProviderId("naver");
        OAuth2User user = new WrongNaverUser();
        /* key 값이 잘못되거나 attribute 가 비어있을 때 NPE 발생 -> 에러처리 */
        assertThrows(CustomAuthenticationException.class,
                () -> naver.getEmail(user.getAttributes()));
    }

    static class GoogleUser implements OAuth2User{

        @Override
        public <A> A getAttribute(String name) {
            return null;
        }

        @Override
        public Map<String, Object> getAttributes() {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "googleNickname");
            attributes.put("email", "googleEmail");
            return attributes;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    }

    static class NaverUser implements OAuth2User{

        @Override
        public Map<String, Object> getAttributes() {
            Map<String, Object> attribute = new HashMap<>();
            HashMap<String, String> innerAttribute = new HashMap<>();
            innerAttribute.put("email", "naverEmail");
            innerAttribute.put("nickname", "naverNickname");
            attribute.put("response", innerAttribute);
            return attribute;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    }

    static class WrongNaverUser implements OAuth2User{

        @Override
        public Map<String, Object> getAttributes() {
            Map<String, Object> attribute = new HashMap<>();
            HashMap<String, String> innerAttribute = new HashMap<>();
            innerAttribute.put("wrongEmail", "naverEmail");
            attribute.put("response", innerAttribute);
            return attribute;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    }
}