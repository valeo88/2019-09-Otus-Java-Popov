package ru.otus.hw15.web.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.otus.hw15.api.service.UserService;
import ru.otus.hw15.web.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserWSController {

    private final UserService userService;

    public UserWSController(UserService userService) {
        this.userService = userService;
    }

    @MessageMapping("/getAllUsers")
    @SendTo("/topic/allUsers")
    public List<UserDTO> allUsers() {
        return userService.getAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }


}
