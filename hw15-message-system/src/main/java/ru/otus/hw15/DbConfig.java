package ru.otus.hw15;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw15.api.model.AddressDataSet;
import ru.otus.hw15.api.model.PhoneDataSet;
import ru.otus.hw15.api.model.User;
import ru.otus.hw15.api.service.UserService;
import ru.otus.hw15.hibernate.HibernateUtils;
import ru.otus.hw15.web.startup.AdminUserCreator;

@Configuration
public class DbConfig {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "/WEB-INF/config/hibernate.cfg.xml";

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                User.class, AddressDataSet.class, PhoneDataSet.class);
    }

    @Bean(initMethod = "createDefaultAdminUser")
    public AdminUserCreator adminUserCreator(UserService userService) {
        return new AdminUserCreator(userService);
    }
}
