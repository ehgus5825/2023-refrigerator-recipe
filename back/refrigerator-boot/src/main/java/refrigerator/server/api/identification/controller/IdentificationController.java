//package refrigerator.server.api.identification.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import refrigerator.back.identification.application.port.in.CheckNumberUseCase;
//import refrigerator.back.identification.application.port.in.SendNumberUseCase;
//import refrigerator.server.api.global.exception.ValidationExceptionHandler;
//import refrigerator.server.api.identification.dto.CheckNumberRequestDTO;
//import refrigerator.server.api.identification.dto.SendNumberRequestDTO;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//
//import static refrigerator.back.comment.exception.CommentExceptionType.*;
//
//@RestController
//@RequiredArgsConstructor
//public class IdentificationController {
//
//    private final CheckNumberUseCase checkNumberUseCase;
//    private final SendNumberUseCase sendNumberUseCase;
//
//    @PostMapping("/api/identification/send")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void send(@RequestBody SendNumberRequestDTO request, BindingResult bindingResult){
//        ValidationExceptionHandler.check(bindingResult, NOT_VALID_REQUEST_BODY);
//
//        sendNumberUseCase.sendAuthenticationNumber(
//                request.getEmail(),
//                Integer.valueOf(1000 * 60 * 30).longValue());
//    }
//
//    @PostMapping("/api/identification/check")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void checkNumber(@RequestBody CheckNumberRequestDTO request, HttpServletResponse response, BindingResult bindingResult){
//        ValidationExceptionHandler.check(bindingResult, NOT_VALID_REQUEST_BODY);
//
//        checkNumberUseCase.checkAuthenticationNumber(request.getInputCode(), request.getEmail());
//        Cookie cookie = new Cookie("Verified-User", request.getEmail());
//        cookie.setHttpOnly(true);
//        cookie.setPath("/api/members/join");
//        cookie.setMaxAge(3 * 60);
//        response.addCookie(cookie);
//    }
//}
