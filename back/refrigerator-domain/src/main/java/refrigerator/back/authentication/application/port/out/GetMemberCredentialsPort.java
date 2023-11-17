package refrigerator.back.authentication.application.port.out;

import refrigerator.back.authentication.application.dto.UserDto;

/**
 * JWT 토큰을 파싱한 정보(UserDto)를 가져옴
 */
public interface GetMemberCredentialsPort {
    UserDto getUser(String username);
}
