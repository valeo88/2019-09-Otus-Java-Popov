package ru.otus.hw13.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    public IndexController() {
    }

    @GetMapping("/")
    public String indexView(Model model) {
        return "index.html";
    }

}
