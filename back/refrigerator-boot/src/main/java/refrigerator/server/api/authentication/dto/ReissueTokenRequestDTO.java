package refrigerator.server.api.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReissueTokenRequestDTO {
    private String refreshToken;
}
