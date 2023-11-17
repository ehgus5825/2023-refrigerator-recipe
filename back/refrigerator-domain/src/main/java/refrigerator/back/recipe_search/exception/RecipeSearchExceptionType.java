package refrigerator.back.recipe_search.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

import static refrigerator.back.global.exception.BasicHttpStatus.*;

@AllArgsConstructor
@Getter
public enum RecipeSearchExceptionType implements BasicExceptionType {
    NOT_FOUND_RECIPE_LIST("NOT_FOUND_RECIPE_LIST", "레시피 목록을 가져올 수 없습니다.", BAD_REQUEST),
    INVALID_RECIPE_DATA("INVALID_RECIPE_DATA", "레시피 데이터를 가져올 수 없습니다.", BAD_REQUEST),
    EMPTY_RECIPE_SEARCH_WORD("EMPTY_RECIPE_SEARCH_WORD", "검색어를 입력해주세요.", BAD_REQUEST),
    REDIS_TYPE_ERROR("SYSTEM_ERROR", "시스템 상의 오류가 발생했습니다. 다시 시도해주세요.", BAD_REQUEST)
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
