package refrigerator.back.recipe_searchword.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

import static refrigerator.back.global.exception.BasicHttpStatus.*;

@AllArgsConstructor
@Getter
public enum SearchWordExceptionType implements BasicExceptionType {
    EMPTY_WORD_LIST("EMPTY_WORD_LIST", "검색어를 찾을 수 없습니다.", BAD_REQUEST)
    ;

    private final String errorCode;
    private final String message;
    private final BasicHttpStatus httpStatus;

    @Override
    public String getErrorCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public BasicHttpStatus getHttpStatus() {
        return null;
    }
}
