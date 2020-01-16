package ru.otus.hw13.web.startup;

import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.service.UserService;

public class AdminUserCreator {

    private final UserService userService;

    public AdminUserCreator(UserService userService) {
        this.userService = userService;
    }

    public void createDefaultAdminUser() {
        User admin = new User("Admin");
        admin.setLogin("admin");
        admin.setPassword("123");
        admin.setIsAdmin(true);

        userService.saveUser(admin);
    }
}
