package ru.otus.hw13.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.service.UserService;
import ru.otus.hw13.api.service.UserServiceException;
import ru.otus.hw13.web.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user")
    public List<UserDTO> allUsers() {
        List<UserDTO> dtos = new ArrayList<>();
        userService.getAll().forEach(user -> {
            dtos.add(userToDTO(user));
        });

        return dtos;
    }

    @GetMapping("/api/user/{id}")
    public UserDTO user(@PathVariable("id") String id) {
        return userToDTO(userService.getUser(id).orElse(null));
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

    private UserDTO userToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        if (user!=null) {
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setLogin(user.getLogin());
            userDTO.setIsAdmin(user.getIsAdmin());
            userDTO.setPassword("******");
        }

        return userDTO;
    }
}
