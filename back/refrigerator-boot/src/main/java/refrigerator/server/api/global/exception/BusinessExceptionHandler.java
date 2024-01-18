package refrigerator.server.api.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import refrigerator.back.global.exception.BasicException;
import refrigerator.back.global.exception.BasicExceptionType;
import refrigerator.back.global.exception.BusinessException;
import refrigerator.back.global.exception.ValidException;

import javax.validation.ConstraintViolationException;
import java.net.ConnectException;

import static refrigerator.server.api.global.exception.BasicExceptionFormat.*;

@Slf4j
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BasicExceptionFormat> businessException(BusinessException e){

        return new ResponseEntity<>(create(e.getBasicExceptionType()), HttpStatus.valueOf(e.getBasicExceptionType().getHttpStatus().getCode()));
    }

    @ExceptionHandler(BasicException.class)
    public ResponseEntity<BasicExceptionFormat> basicException(BasicException e){
        BasicExceptionType basicExceptionType = e.getBasicExceptionType();
        return new ResponseEntity<>(create(basicExceptionType), HttpStatus.valueOf(basicExceptionType.getHttpStatus().toString()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BasicExceptionFormat> ConstraintViolationException(ConstraintViolationException e){
        return new ResponseEntity<>(new BasicExceptionFormat("NOT_VALID_REQUEST_PARAM", "입력하신 파라미터가 유효하지 않습니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BasicExceptionFormat> MissingServletRequestParameterException(MissingServletRequestParameterException e){
        return new ResponseEntity<>(new BasicExceptionFormat("MISSING_REQUEST_PARAMETER", "파라미터가 비어있습니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BasicExceptionFormat> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        return new ResponseEntity<>(new BasicExceptionFormat("TYPE_MISMATCH_ERROR", "파라미터의 형식이 맞지 않습니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidException.class)
    public ResponseEntity<BasicExceptionFormat> ValidationException(ValidException e){
        return new ResponseEntity<>(new BasicExceptionFormat(e.code, e.message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<BasicExceptionFormat> oauth2AuthenticationException(OAuth2AuthenticationException e){
        return new ResponseEntity<>(new BasicExceptionFormat("FAIL_OAUTH2_LOGIN", "간편 로그인에 실패하였습니다."), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<BasicExceptionFormat> notDatabaseConnected(ConnectException e){
        return new ResponseEntity<>(new BasicExceptionFormat("NOT_CONNECTED_SERVER", "현재 서버의 연결이 원활하지 않습니다."), HttpStatus.NOT_FOUND);
    }

}
