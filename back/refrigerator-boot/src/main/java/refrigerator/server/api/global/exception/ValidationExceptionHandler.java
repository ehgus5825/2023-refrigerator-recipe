package refrigerator.server.api.global.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.exception.ValidException;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

import static java.util.regex.Pattern.matches;

public class ValidationExceptionHandler {

    public static void check(BindingResult result, BasicExceptionType exceptionType) {
        if (result.hasErrors()) {
            throw new BusinessException(exceptionType);
        }
    }

    public static void patternCheck(String nameRegex, String name, BasicExceptionType exceptionType) {
        if(!matches(nameRegex, name))
            throw new BusinessException(exceptionType);
    }

    public static void positiveOrZeroCheck(Number number) {
        if(number.longValue() < 0){
            throw new ConstraintViolationException("", null);
        }
    }

    public static void positiveCheck(Number number) {
        if(number.longValue() <= 0){
            throw new ConstraintViolationException("", null);
        }
    }

    public static void multiCheck(BindingResult result) {
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {

                if (Objects.equals(error.getCode(), "NotBlank")) {
                    throw new ValidException("EMPTY_INPUT_DATA", error.getField() + "의 값이 비어있습니다.");
                } else if (Objects.equals(error.getCode(), "NotNull")) {
                    throw new ValidException("NULL_INPUT_DATA", error.getField() + "의 값이 NULL입니다.");
                } else if (Objects.equals(error.getCode(), "Positive")) {
                    throw new ValidException("NUMBER_GREATER_THAN_ZERO", error.getField() + "의 값은 0보다 큰 수만 가능합니다.");
                } else if (Objects.equals(error.getCode(), "PositiveOrZero")) {
                    throw new ValidException("NUMBER_GREATER_THAN_OR_EQUAL_TO_ZERO", error.getField() + "의 값은 0 이상의 수만 가능합니다.");
                } else if (Objects.equals(error.getCode(), "Pattern")) {
                    throw new ValidException("INCORRECT_DATA_FORMAT", error.getField() + "의 형식에 어긋납니다.");
                } else {
                    throw new ValidException("EMPTY_INPUT_DATA", error.getField() + "의 값이 유효하지 않습니다.");
                }
            }
        }
    }
}