package refrigerator.server.api.member.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import refrigerator.server.api.global.common.InputDataFormatCheck;

import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NicknameModifyRequestDto {
    @Pattern(regexp = InputDataFormatCheck.NICKNAME_REGEX)
    private String nickname;
}
