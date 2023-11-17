package refrigerator.server.api.global.exception;

import lombok.Getter;
import refrigerator.back.global.exception.BasicExceptionType;

@Getter
public class BasicExceptionFormat {
    private String code;
    private String message;

    public static BasicExceptionFormat create(BasicExceptionType baseExceptionType){
        return new BasicExceptionFormat(baseExceptionType.getErrorCode(), baseExceptionType.getMessage());
    }

    public BasicExceptionFormat(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
