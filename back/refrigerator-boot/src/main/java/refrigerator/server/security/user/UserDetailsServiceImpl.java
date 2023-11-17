package refrigerator.server.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import refrigerator.back.authentication.application.dto.UserDto;
import refrigerator.back.authentication.application.port.out.GetMemberCredentialsPort;


@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final GetMemberCredentialsPort getMemberUseCase;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(getMemberUseCase.getUser(username));
    }
}
