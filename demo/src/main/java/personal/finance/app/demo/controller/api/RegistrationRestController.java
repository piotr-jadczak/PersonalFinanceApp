package personal.finance.app.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import personal.finance.app.demo.domain.dto.UserDto;
import personal.finance.app.demo.domain.entity.user.User;
import personal.finance.app.demo.service.contract.RegistrationService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
public class RegistrationRestController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationRestController(RegistrationService loginService) {
        this.registrationService = loginService;
    }

    @PostMapping("/api/register")
    @ResponseStatus(HttpStatus.CREATED)
    User registerUser(@RequestBody @Valid UserDto userDto) throws MessagingException {

        User userToRegister = registrationService.mapUser(userDto);
        return registrationService.registerUser(userToRegister);
    }

    @GetMapping("/confirm-registration/{tokenLink}")
    @ResponseStatus(HttpStatus.OK)
    String activateAccount(@PathVariable("tokenLink") String tokenLink) {
        registrationService.activateAccount(tokenLink);
        return "Your account has been activated";
    }
}
