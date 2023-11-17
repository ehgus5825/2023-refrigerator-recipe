package refrigerator.back.authentication.outbound.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import refrigerator.back.authentication.outbound.repository.UserSelectQueryRepository;
import refrigerator.back.authentication.application.dto.UserDto;
import refrigerator.back.authentication.application.port.out.GetMemberCredentialsPort;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.member.exception.MemberExceptionType;

@Component
@RequiredArgsConstructor
public class MemberCredentialsAdapter implements GetMemberCredentialsPort {

    private final UserSelectQueryRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(String username) {
        return repository.selectUserByName(username)
                .orElseThrow(() -> new BusinessException(MemberExceptionType.NOT_FOUND_MEMBER))
                .mapping();
    }
}
