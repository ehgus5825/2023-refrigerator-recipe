package refrigerator.server.security.authentication.application.service;

import refrigerator.back.global.exception.BasicException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MakeResponseMessageService {
    public static void makeResponseMessage(HttpServletResponse response, BasicException e) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(
                "{\"error\": \""+ e.getBasicExceptionType().getErrorCode()+"\", \"message\" : \""+
                        e.getBasicExceptionType().getMessage()+"\"}");
    }
}
