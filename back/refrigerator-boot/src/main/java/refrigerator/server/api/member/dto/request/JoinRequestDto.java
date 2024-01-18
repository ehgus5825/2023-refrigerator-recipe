package refrigerator.server.api.member.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.server.api.global.common.InputDataFormatCheck;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequestDto {

    @Pattern(regexp = InputDataFormatCheck.EMAIL_REGEX)
    private String email;

    @Pattern(regexp = InputDataFormatCheck.PASSWORD_REGEX)
    private String password;

    @Pattern(regexp = InputDataFormatCheck.NICKNAME_REGEX)
    private String nickname;
}
