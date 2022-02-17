package personal.finance.app.demo.service.contract;

import personal.finance.app.demo.domain.dto.UserDto;
import personal.finance.app.demo.domain.entity.User;
import personal.finance.app.demo.domain.entity.VerificationToken;

import javax.mail.MessagingException;

public interface LoginService {

    User addUser(User userToAdd);
    User mapUser(UserDto userDto);
    VerificationToken generateVerificationToken(long userId);
    void sendVerificationTokenOnEmail(String token, String recipientEmail)
            throws MessagingException;
    User registerUser(User userToRegister) throws MessagingException;
    VerificationToken findVerificationToken(String token);
    void deleteToken(VerificationToken token);
    void enableUserLogin(VerificationToken token);
    void activateAccount(String tokenLink);
}
