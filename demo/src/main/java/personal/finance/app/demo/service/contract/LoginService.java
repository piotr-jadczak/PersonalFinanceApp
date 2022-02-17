package personal.finance.app.demo.service.contract;

import personal.finance.app.demo.domain.dto.UserDto;
import personal.finance.app.demo.domain.entity.User;

public interface LoginService {

    User registerUser(User userToRegister);
    User mapUser(UserDto userDto);
}
