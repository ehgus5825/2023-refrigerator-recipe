package refrigerator.back.myscore.exception;

import lombok.AllArgsConstructor;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

import static refrigerator.back.global.exception.BasicHttpStatus.*;

@AllArgsConstructor
public enum MyScoreExceptionType implements BasicExceptionType {

    NOT_FOUND_SCORE("NOT_FOUND_SCORE", "해당 레시피 점수를 가져오지 못했습니다.", NOT_FOUND),
    FAIL_MODIFY_SCORE("FAIL_MODIFY_SCORE", "별점을 수정할 수 없습니다.", NOT_FOUND),
    WRONG_SCORE_SCOPE("WRONG_SCORE_SCOPE", "허용하지 않는 별점의 범위입니다.", BAD_REQUEST)
    ;

    private String errorCode;
    private String message;
    private BasicHttpStatus httpStatus;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public BasicHttpStatus getHttpStatus() {
        return httpStatus;
    }
}
