package refrigerator.server.api.authentication.inbound.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryAccessTokenResponseDTO {
    private String grantType;
    private String authToken;
}
