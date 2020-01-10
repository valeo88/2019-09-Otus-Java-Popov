package ru.otus.hw13.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.web.service.UserAuthService;

@Controller
public class LoginController {

    private final UserAuthService userAuthService;

    public LoginController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping("/login")
    public String loginView(Model model) {
        return "login.html";
    }

    @PostMapping("/login")
    public RedirectView login(@ModelAttribute User user) {
        if (userAuthService.authenticate(user.getLogin(), user.getPassword())) {
            return new RedirectView("/admin", true);
        } else {
            RedirectView redirectView = new RedirectView("/", true);
            redirectView.setStatusCode(HttpStatus.UNAUTHORIZED);
            return redirectView;
        }
    }

}
