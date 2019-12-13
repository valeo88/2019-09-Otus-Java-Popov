package ru.otus.hw10;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10.api.dao.UserDao;
import ru.otus.hw10.api.model.AddressDataSet;
import ru.otus.hw10.api.model.PhoneDataSet;
import ru.otus.hw10.api.model.User;
import ru.otus.hw10.api.service.DBServiceUser;
import ru.otus.hw10.api.service.DbServiceUserImpl;
import ru.otus.hw10.hibernate.HibernateUtils;
import ru.otus.hw10.hibernate.dao.UserDaoHibernate;
import ru.otus.hw10.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Demo {
    private static Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class,
                AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        AddressDataSet addressDataSet = new AddressDataSet(0, "Советская");
        Set<PhoneDataSet> phones = new HashSet<>();
        phones.add(new PhoneDataSet(0, "+7-111-123-45-67"));

        long id = dbServiceUser.saveUser(new User(0, "Котя", addressDataSet, phones));
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        id = dbServiceUser.saveUser(new User(1L, "Мотя"));
        Optional<User> mayBeUpdatedUser = dbServiceUser.getUser(id);

        outputUserOptional("Created user", mayBeCreatedUser);
        outputUserOptional("Updated user", mayBeUpdatedUser);
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }
}
