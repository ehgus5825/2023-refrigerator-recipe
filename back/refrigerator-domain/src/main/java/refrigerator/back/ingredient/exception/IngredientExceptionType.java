package refrigerator.back.ingredient.exception;

import lombok.AllArgsConstructor;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

import static refrigerator.back.global.exception.BasicHttpStatus.*;

@AllArgsConstructor
public enum IngredientExceptionType implements BasicExceptionType {
    NO_PREVIOUSLY_REGISTERED_INGREDIENTS("NO_PREVIOUSLY_REGISTERED_INGREDIENTS", "기존에 등록된 식재료가 없습니다.", NOT_FOUND),
    NOT_A_PREVIOUSLY_REGISTERED_INGREDIENT("NOT_A_PREVIOUSLY_REGISTERED_INGREDIENT", "기존에 등록된 식재료가 아닙니다.", NOT_FOUND),
    NOT_FOUND_INGREDIENT("NOT_FOUND_INGREDIENT", "해당 식재료를 찾을 수 없습니다.", NOT_FOUND),
    EMPTY_INGREDIENT_LIST("EMPTY_INGREDIENT_LIST", "등록된 식재료가 없습니다.", BAD_REQUEST),
    CHECK_INGREDIENT_STORAGE_METHOD("CHECK_INGREDIENT_STORAGE_METHOD", "보관 방법을 확인해 주세요.", BAD_REQUEST),
    NOT_FOUND_REGISTERED_INGREDIENT("NOT_FOUND_REGISTERED_INGREDIENT", "등록되어 있지 않은 식재료입니다.", NOT_FOUND),
    NOT_FOUND_REGISTERED_RECIPE("NOT_FOUND_REGISTERED_RECIPE", "등록되어 있지 않은 레시피입니다.", NOT_FOUND),
    NOT_VALID_REQUEST_BODY("NOT_VALID_REQUEST_BODY", "요청하신 데이터가 유효하지 않습니다.", BAD_REQUEST),
    NOT_VALID_INGREDIENTS_TO_BE_DEDUCTED("NOT_VALID_INGREDIENTS_TO_BE_DEDUCTED", "차감될 식재료가 유효하지 않습니다.", BAD_REQUEST),
    FAILED_TO_DELETE_INGREDIENTS("FAILED_TO_DELETE_INGREDIENTS", "식재료를 삭제하는데 실패하였습니다.", BAD_REQUEST),
    EXCEEDED_CAPACITY_RANGE("EXCEEDED_CAPACITY_RANGE", "용량 범위를 초과하였습니다.", BAD_REQUEST),
    INVALID_DATE("INVALID_DATE", "잘못된 날짜 값 입니다.", BAD_REQUEST),
    ERROR_INGREDIENT_MATCHING("ERROR_INGREDIENT_MATCHING", "식재료 매칭 오류가 발생하였습니다.", BAD_REQUEST),
    CAPACITY_MODIFICATION_FAILED("CAPACITY_MODIFICATION_FAILED", "용량을 수정하는 데 실패하였습니다.", BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final BasicHttpStatus httpStatus;

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
