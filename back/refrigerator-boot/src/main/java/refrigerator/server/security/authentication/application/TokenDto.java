package refrigerator.server.security.authentication.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    public void removeRefreshToken(){
        this.refreshToken = null;
    }
}
