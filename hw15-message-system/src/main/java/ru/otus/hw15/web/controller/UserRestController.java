package ru.otus.hw15.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw15.api.model.User;
import ru.otus.hw15.api.service.UserService;
import ru.otus.hw15.api.service.UserServiceException;
import ru.otus.hw15.web.dto.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user")
    public List<UserDTO> allUsers() {
        return userService.getAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/api/user/{id}")
    public UserDTO user(@PathVariable("id") String id) {
        return new UserDTO(userService.getUser(id).orElse(null));
    }

    @PostMapping("/api/user")
    public ResponseEntity<String> createUser(@RequestBody UserDTO data) {
        User newUser = new User(data.getName());
        newUser.setLogin(data.getLogin());
        newUser.setPassword(data.getPassword());
        newUser.setIsAdmin(data.getIsAdmin());

        try {
            userService.saveUser(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_HTML)
                    .body(e.getMessage());
        }
    }
}
