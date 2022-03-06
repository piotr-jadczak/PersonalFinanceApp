package personal.finance.app.demo.controller.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String viewMainPage() {
        return "main/index";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "main/login";
    }

    @GetMapping("/register")
    public String viewRegisterPage(Model model) {
        return "main/register";
    }

    @GetMapping("/success_login")
    public String viewSuccessLoginPage(Model model) {

        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("helloMessage", "Hello " + login + "!");
        return "main/index";
    }
}
