package ru.otus.hw13.web.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.service.UserService;
import ru.otus.hw13.api.service.UserServiceException;

@RestController
public class UserRestController {
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_NAME = "name";
    private static final String IS_ADMIN = "isAdmin";

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user")
    public JsonArray allUsers() {
        return usersToJsonArray();
    }

    @GetMapping("/api/user/{id}")
    public JsonObject user(@PathVariable("id") String id) {
        return userToJsonObject(userService.getUser(id).orElse(null));
    }

    @PostMapping("/api/user")
    public ResponseEntity<String> createUser(@RequestParam(PARAM_LOGIN) String login,
                                             @RequestParam(PARAM_PASSWORD) String password,
                                             @RequestParam(PARAM_NAME) String name,
                                             @RequestParam(IS_ADMIN) Boolean isAdmin) {

        User newUser = new User(name);
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setIsAdmin(isAdmin);

        try {
            userService.saveUser(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_HTML)
                    .body(e.getMessage());
        }
    }

    private JsonObject userToJsonObject(User user) {
        JsonObject userObject = new JsonObject();
        if (user!=null) {
            userObject.add("id", new JsonPrimitive(user.getId()));
            userObject.add("name", new JsonPrimitive(user.getName()));
            userObject.add("login", new JsonPrimitive(user.getLogin()));
            userObject.add("password", new JsonPrimitive("******"));
        }

        return userObject;
    }

    private JsonArray usersToJsonArray() {
        JsonArray jsonArray = new JsonArray();
        userService.getAll().forEach(user -> {
            jsonArray.add(userToJsonObject(user));
        });
        return jsonArray;
    }
}
