package personal.finance.app.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import personal.finance.app.demo.domain.dto.UserDto;
import personal.finance.app.demo.domain.entity.User;
import personal.finance.app.demo.service.contract.LoginService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
public class RegistrationRestController {

    private final LoginService loginService;

    @Autowired
    public RegistrationRestController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/register")
    @ResponseStatus(HttpStatus.CREATED)
    User registerUser(@RequestBody @Valid UserDto userDto) throws MessagingException {

        User userToRegister = loginService.mapUser(userDto);
        return loginService.registerUser(userToRegister);
    }

    @GetMapping("/confirm-registration/{tokenLink}")
    @ResponseStatus(HttpStatus.OK)
    String activateAccount(@PathVariable("tokenLink") String tokenLink) {
        loginService.activateAccount(tokenLink);
        return "Your account has been activated";
    }
}
