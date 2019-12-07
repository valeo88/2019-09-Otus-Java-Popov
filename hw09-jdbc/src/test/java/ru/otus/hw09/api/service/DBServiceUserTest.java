package ru.otus.hw09.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw09.api.dao.UserDao;
import ru.otus.hw09.api.model.User;
import ru.otus.hw09.h2.DataSourceH2;
import ru.otus.hw09.h2.mappers.UserMapper;
import ru.otus.hw09.jdbc.dao.UserDaoJdbc;
import ru.otus.hw09.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты сервиса пользователей. ")
class DBServiceUserTest {

    private DataSource dataSource;
    private SessionManagerJdbc sessionManager;
    private UserDao userDao;
    private DBServiceUser dbServiceUser;

    private User user;

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = new DataSourceH2();
        sessionManager = new SessionManagerJdbc(dataSource);

        new UserMapper(dataSource).createTable();

        userDao = new UserDaoJdbc(sessionManager);
        dbServiceUser = new DbServiceUserImpl(userDao);

        user = new User();
        user.setId(1);
        user.setName("Nikita");
        user.setAge(29);
    }

    @Test
    @DisplayName("Сохранение данных пользователя в БД если он не существует.")
    void saveUserIfNotExists() {
        long id = dbServiceUser.saveUser(user);

        assertEquals(user.getId(), id);
    }

    @Test
    @DisplayName("Сохранение данных пользователя в БД если он существует.")
    void saveUserIfExists() {

        dbServiceUser.saveUser(user);

        user.setAge(50);
        dbServiceUser.saveUser(user);
        User loaded = dbServiceUser.getUser(user.getId()).get();

        assertEquals(user, loaded);

    }

    @Test
    @DisplayName("Получение данных пользователя из БД.")
    void getUser() {
        dbServiceUser.saveUser(user);
        User loaded = dbServiceUser.getUser(user.getId()).get();

        assertEquals(loaded, loaded);

        Optional<User> notExists = dbServiceUser.getUser(100);
        assertEquals(Optional.empty(), notExists);
    }
}