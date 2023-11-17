package refrigerator.server.api.member.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.server.api.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequestDto extends InputDataFormatCheck {

    private String email;
    private String password;
    private String nickname;

    public void check() {
        inputCheck(EMAIL_REGEX, email, MemberExceptionType.INCORRECT_EMAIL_FORMAT);
        inputCheck(PASSWORD_REGEX, password, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
        inputCheck(NICKNAME_REGEX, nickname, MemberExceptionType.INCORRECT_NICKNAME_FORMAT);
    }
}
