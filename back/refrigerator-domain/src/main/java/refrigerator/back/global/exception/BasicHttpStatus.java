package refrigerator.back.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BasicHttpStatus {

    BAD_REQUEST(400),
    NOT_FOUND(404),
    FORBIDDEN(403);

    private Integer code;
}
