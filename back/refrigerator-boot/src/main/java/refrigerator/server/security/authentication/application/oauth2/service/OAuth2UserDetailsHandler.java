package refrigerator.server.security.authentication.application.oauth2.service;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public interface OAuth2UserDetailsHandler {
    OAuth2User get(String providerId, Map<String, Object> attributes);
}
