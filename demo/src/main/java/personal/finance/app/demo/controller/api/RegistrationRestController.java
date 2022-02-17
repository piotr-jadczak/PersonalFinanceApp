package personal.finance.app.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import personal.finance.app.demo.domain.dto.UserDto;
import personal.finance.app.demo.domain.entity.User;
import personal.finance.app.demo.service.contract.LoginService;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@Transactional
public class RegistrationRestController {

    private final LoginService loginService;

    @Autowired
    public RegistrationRestController(LoginService loginService) {

        this.loginService = loginService;
    }

    @PostMapping("/api/register")
    @ResponseStatus(HttpStatus.CREATED)
    User registerUser(@RequestBody @Valid UserDto userDto) {
        User userToRegister = loginService.mapUser(userDto);
        return loginService.registerUser(userToRegister);
    }
}
