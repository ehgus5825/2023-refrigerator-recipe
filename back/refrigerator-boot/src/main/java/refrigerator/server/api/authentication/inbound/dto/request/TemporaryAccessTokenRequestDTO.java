package refrigerator.server.api.authentication.inbound.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.server.api.global.common.InputDataFormatCheck;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryAccessTokenRequestDTO {

    @Pattern(regexp = InputDataFormatCheck.EMAIL_REGEX)
    @NotBlank
    private String email;
}
