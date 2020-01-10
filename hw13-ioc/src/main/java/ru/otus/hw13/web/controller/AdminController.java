package ru.otus.hw13.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    public AdminController() {
    }

    @GetMapping("/admin")
    public String adminView(Model model) {
        return "admin.html";
    }

}
