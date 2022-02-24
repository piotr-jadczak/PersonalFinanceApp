package personal.finance.app.demo.service.contract;

import personal.finance.app.demo.domain.dto.UserDto;
import personal.finance.app.demo.domain.entity.user.User;

import javax.mail.MessagingException;

public interface RegistrationService {

    User mapUser(UserDto userDto);
    User registerUser(User userToRegister) throws MessagingException;
    void activateAccount(String tokenLink);
}
