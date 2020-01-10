package ru.otus.hw13.web.startup;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.service.UserService;

@Component
public class AdminUserCreator {

    private final UserService userService;

    public AdminUserCreator(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ContextStartedEvent.class)
    public void onContextStarted() {
        createAdminUser();
    }


    private void createAdminUser() {
        User admin = new User("Admin");
        admin.setLogin("admin");
        admin.setPassword("123");
        admin.setIsAdmin(true);

        userService.saveUser(admin);
    }
}
