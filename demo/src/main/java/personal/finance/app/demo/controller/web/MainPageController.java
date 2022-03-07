package personal.finance.app.demo.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpServerErrorException;
import personal.finance.app.demo.controller.exception.EmailRegistrationMessageException;
import personal.finance.app.demo.domain.dto.UserDto;
import personal.finance.app.demo.domain.entity.user.User;
import personal.finance.app.demo.service.contract.RegistrationService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainPageController {

    private final RegistrationService registrationService;

    @Autowired
    public MainPageController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/")
    public String viewMainPage() {
        return "main/home";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "main/login";
    }

    @GetMapping("/register")
    public String viewRegisterPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "main/register";
    }

    @GetMapping("/app")
    public String viewSuccessLoginPage(Model model) {

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("helloMessage", "Hello " + login + "!");
        return "app/index";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                               BindingResult result, Model model) {

        if(result.hasErrors()) {
            List<String> formErrors = result.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            model.addAttribute("formErrors", formErrors);
            return "main/register";
        }
        User userToRegister = registrationService.mapUser(userDto);
        try {
            registrationService.registerUser(userToRegister);
        }
        catch (MessagingException exception) {
            throw new EmailRegistrationMessageException("Cannot send confirmation email");
        }

        return "main/register-success";
    }
}
