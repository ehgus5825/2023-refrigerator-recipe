package refrigerator.server.api.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.server.api.global.common.InputDataFormatCheck;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailCheckRequestDto {

    @NotBlank
    @Pattern(regexp = InputDataFormatCheck.EMAIL_REGEX)
    private String email;
}
