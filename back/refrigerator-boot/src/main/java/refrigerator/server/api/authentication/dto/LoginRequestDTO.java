package refrigerator.server.api.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.server.api.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO extends InputDataFormatCheck {

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    public void check() {
        inputCheck(EMAIL_REGEX, email, MemberExceptionType.INCORRECT_EMAIL_FORMAT);
        inputCheck(PASSWORD_REGEX, password, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
    }
}
