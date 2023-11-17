//package refrigerator.infra.identification.apdater;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import refrigerator.back.global.exception.BusinessException;
//import refrigerator.back.identification.application.port.out.IdentificationMailSendPort;
//
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//import static refrigerator.back.identification.exception.IdentificationExceptionType.*;
//
//@Component
//@RequiredArgsConstructor
//public class MainIdentificationMethod implements IdentificationMailSendPort {
//
//    private final JavaMailSender mailSender;
//    private final SpringTemplateEngine templateEngine;
//
//    @Override
//    @Async
//    public void sendAuthenticationCode(String email, String code) {
//        MimeMessage message = mailSender.createMimeMessage();
//        try {
//            message.addRecipients(MimeMessage.RecipientType.TO,  email);
//            message.setSubject("[냉장고를 부탁해] 인증코드를 보내드립니다.");
//            message.setText(settingSendView(code), "utf-8", "html");
//            mailSender.send(message);
//
//        } catch (MessagingException e) {
//            throw new BusinessException(FAIL_SEND_EMAIL);
//        }
//    }
//
//
//    private String settingSendView(String code){
//        Context context = new Context();
//        context.setVariable("code", code);
//        return templateEngine.process("mail", context);
//    }
//}
