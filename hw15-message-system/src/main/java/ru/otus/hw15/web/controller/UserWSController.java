package ru.otus.hw15.web.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.hw15.web.service.FrontendService;

@Controller
public class UserWSController {

    private final FrontendService frontendService;
    private final SimpMessagingTemplate template;

    public UserWSController(FrontendService frontendService, SimpMessagingTemplate template) {
        this.frontendService = frontendService;
        this.template = template;
    }

    @MessageMapping("/getAllUsers")
    public void allUsers() {
        frontendService.getAllUsersData(data -> {
            this.template.convertAndSend("/topic/allUsers", data);
        });
    }


}
