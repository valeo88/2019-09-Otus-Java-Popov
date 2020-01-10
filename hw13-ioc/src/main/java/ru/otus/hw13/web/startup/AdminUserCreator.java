package ru.otus.hw13.web.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.service.UserService;

@Component
public class AdminUserCreator implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(AdminUserCreator.class);

    private final UserService userService;

    public AdminUserCreator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        createAdminUser();
    }

    private void createAdminUser() {
        logger.info("Creating default admin user");
        User admin = new User("Admin");
        admin.setLogin("admin");
        admin.setPassword("123");
        admin.setIsAdmin(true);

        userService.saveUser(admin);
    }


}
