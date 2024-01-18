package refrigerator.server.security.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ClaimsDto {
    private String email;
    private String role;
    private Date expiration;
}
