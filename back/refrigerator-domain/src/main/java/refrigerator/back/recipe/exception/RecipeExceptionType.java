package refrigerator.back.recipe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BasicHttpStatus;

import static refrigerator.back.global.exception.BasicHttpStatus.*;

@AllArgsConstructor
@Getter
public enum RecipeExceptionType implements BasicExceptionType {
    NOT_FOUND_RECIPE("NOT_FOUND_RECIPE", "레시피를 찾을 수 없습니다.", BAD_REQUEST),
    WRONG_DIFFICULTY("WRONG_DIFFICULTY", "잘못된 난이도 입니다.", NOT_FOUND),
    WRONG_INGREDIENT_TYPE("WRONG_INGREDIENT_TYPE", "잘못된 재료 타입입니다.", NOT_FOUND),
    WRONG_RECIPE_TYPE("WRONG_RECIPE_TYPE", "잘못된 레시피 타입입니다.", NOT_FOUND),
    NOT_ACCEPTABLE_RANGE("NOT_ACCEPTABLE_RANGE", "허용된 별점의 범위가 아닙니다.", BAD_REQUEST),
    EMPTY_MEMBER_INGREDIENT("EMPTY_MEMBER_INGREDIENT", "사용자가 등록한 식재료가 없습니다.", BAD_REQUEST),
    EMPTY_RECIPE_SEARCH_WORD("EMPTY_RECIPE_SEARCH_WORD", "검색어를 입력해주세요.", BAD_REQUEST),
    NOT_FOUND_RECIPE_SCORE("NOT_FOUND_RECIPE_SCORE", "해당 레시피의 별점을 찾을 수 없습니다.", BAD_REQUEST),
    NOT_FOUND_RECIPE_BOOKMARK("NOT_FOUND_RECIPE_BOOKMARK", "해당 레시피의 북마크를 찾을 수 없습니다.", BAD_REQUEST),
    NOT_FOUND_RECIPE_VIEW("NOT_FOUND_RECIPE_VIEW", "레시피 조회수를 찾을 수 없습니다.", BAD_REQUEST),
    NOT_FOUND_RECIPE_SCORE_PERSON("NOT_FOUND_RECIPE_SCORE_PERSON", "해당 레시피에 별점을 남긴 회원이 존재하지 않습니다.", BAD_REQUEST),
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
