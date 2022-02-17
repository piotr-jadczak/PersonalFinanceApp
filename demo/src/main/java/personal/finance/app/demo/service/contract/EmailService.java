package personal.finance.app.demo.service.contract;

import javax.mail.MessagingException;

public interface EmailService {

    void sendMail(String to, String subject,
                  String text, boolean isHtmlContent) throws MessagingException;
}
