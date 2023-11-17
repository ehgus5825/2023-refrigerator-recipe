package refrigerator.server.security.user;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import refrigerator.back.authentication.application.dto.UserDto;
import refrigerator.back.authentication.application.port.out.GetMemberCredentialsPort;
import refrigerator.back.global.exception.BasicException;
import refrigerator.back.member.application.domain.MemberStatus;
import refrigerator.back.member.application.port.in.JoinUseCase;
import refrigerator.back.member.exception.MemberExceptionType;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Builder
public class OAuthUser implements OAuth2User {

    private final String username;
    private final String password;
    private final String authority;
    private final Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getName() {
        return username;
    }
}
