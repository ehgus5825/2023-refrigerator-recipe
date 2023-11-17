package refrigerator.server.security.authentication.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenStatus {
    PASS("정상", "사용 가능한 토큰입니다."),
    EXPIRED("만료", "만료된 토큰입니다."),
    WRONG("오류", "잘못된 토큰입니다.")
    ;

    private final String status;
    private final String detailMessage;
}
