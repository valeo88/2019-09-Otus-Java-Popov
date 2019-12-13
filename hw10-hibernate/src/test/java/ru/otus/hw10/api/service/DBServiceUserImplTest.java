package ru.otus.hw10.api.service;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw10.api.dao.UserDao;
import ru.otus.hw10.api.model.AddressDataSet;
import ru.otus.hw10.api.model.PhoneDataSet;
import ru.otus.hw10.api.model.User;
import ru.otus.hw10.hibernate.HibernateUtils;
import ru.otus.hw10.hibernate.dao.UserDaoHibernate;
import ru.otus.hw10.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с пользователями в рамках БД должен ")
class DBServiceUserImplTest {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";

    private static final long USER_ID = 1L;

    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManager;
    private UserDao userDao;
    private DBServiceUser dbServiceUser;


    @BeforeEach
    void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                User.class, AddressDataSet.class, PhoneDataSet.class);
        sessionManager = new SessionManagerHibernate(sessionFactory);
        userDao = new UserDaoHibernate(sessionManager);
        dbServiceUser = new DbServiceUserImpl(userDao);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    @Test
    @DisplayName(" корректно сохранять пользователя без связей с другими сущностями.")
    void shouldSaveUserWithoutRelations() {
        User user = new User(0, "Иван");

        long id = dbServiceUser.saveUser(user);
        assertThat(id).isEqualTo(USER_ID);

        Optional<User> mayBeUser = dbServiceUser.getUser(id);
        assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(user);
    }

}