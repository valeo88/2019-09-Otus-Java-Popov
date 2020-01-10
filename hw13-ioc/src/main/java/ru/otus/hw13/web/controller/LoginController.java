package ru.otus.hw13.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.web.service.UserAuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private static final int MAX_INACTIVE_INTERVAL = 30;

    private final UserAuthService userAuthService;

    public LoginController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping("/login")
    public String view() {
        return "login.html";
    }

    @PostMapping("/login")
    public RedirectView login(@ModelAttribute User user, HttpServletRequest request) {
        if (userAuthService.authenticate(user.getLogin(), user.getPassword())) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
            return new RedirectView("/admin", true);
        } else {
            RedirectView redirectView = new RedirectView("/", true);
            redirectView.setStatusCode(HttpStatus.UNAUTHORIZED);
            return redirectView;
        }
    }

}
