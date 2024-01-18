package refrigerator.back.global.exception;

import lombok.Getter;

@Getter
public class ValidException extends RuntimeException {

    public final String code;
    public final String message;

    public ValidException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
