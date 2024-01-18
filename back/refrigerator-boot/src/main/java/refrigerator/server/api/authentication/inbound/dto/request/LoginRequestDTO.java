package refrigerator.server.api.authentication.inbound.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.server.api.global.common.InputDataFormatCheck;

import javax.validation.constraints.Pattern;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @Pattern(regexp = InputDataFormatCheck.EMAIL_REGEX)
    private String email;

    @Pattern(regexp = InputDataFormatCheck.PASSWORD_REGEX)
    private String password;
}
