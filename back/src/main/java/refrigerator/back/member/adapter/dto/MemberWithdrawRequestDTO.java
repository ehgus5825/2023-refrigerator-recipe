package refrigerator.back.member.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import refrigerator.back.global.common.InputDataFormatCheck;
import refrigerator.back.member.exception.MemberExceptionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberWithdrawRequestDTO extends InputDataFormatCheck {

    private String password;

    @Override
    public void check() {
        inputCheck(PASSWORD_REGEX, password, MemberExceptionType.INCORRECT_PASSWORD_FORMAT);
    }
}