package ru.otus.hw13;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw13.api.model.AddressDataSet;
import ru.otus.hw13.api.model.PhoneDataSet;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.hibernate.HibernateUtils;

@Configuration
@ComponentScan
public class DbConfig {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "/WEB-INF/config/hibernate.cfg.xml";

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                User.class, AddressDataSet.class, PhoneDataSet.class);
    }
}
