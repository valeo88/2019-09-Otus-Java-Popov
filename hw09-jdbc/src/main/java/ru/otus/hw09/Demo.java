package ru.otus.hw09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.api.dao.AccountDao;
import ru.otus.hw09.api.dao.UserDao;
import ru.otus.hw09.api.model.Account;
import ru.otus.hw09.api.model.User;
import ru.otus.hw09.api.service.DBServiceAccount;
import ru.otus.hw09.api.service.DBServiceUser;
import ru.otus.hw09.api.service.DbServiceAccountImpl;
import ru.otus.hw09.api.service.DbServiceUserImpl;
import ru.otus.hw09.h2.DataSourceH2;
import ru.otus.hw09.h2.mappers.AccountMapper;
import ru.otus.hw09.h2.mappers.UserMapper;
import ru.otus.hw09.jdbc.dao.AccountDaoJdbc;
import ru.otus.hw09.jdbc.dao.UserDaoJdbc;
import ru.otus.hw09.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;

public class Demo {
    private static Logger logger = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) throws SQLException {
        DataSource dataSource = new DataSourceH2();
        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);

        new UserMapper(dataSource).createTable();
        new AccountMapper(dataSource).createTable();

        UserDao userDao = new UserDaoJdbc(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        // создание несуществующих пользователей
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Ivan");
        user1.setAge(29);

        dbServiceUser.saveUser(user1);
        dbServiceUser.getUser(user1.getId());

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Nikita");
        user2.setAge(12);

        dbServiceUser.saveUser(user2);
        dbServiceUser.getUser(user2.getId());

        // изменение существующего
        user1.setName("Mika");
        user1.setAge(50);

        dbServiceUser.saveUser(user1);
        dbServiceUser.getUser(user1.getId());

        AccountDao accountDao = new AccountDaoJdbc(sessionManager);
        DBServiceAccount dbServiceAccount = new DbServiceAccountImpl(accountDao);

        // создание аккаунта
        Account account = new Account();
        account.setNo(1);
        account.setType("First");
        account.setRest(new BigDecimal(100.1));

        dbServiceAccount.saveAccount(account);
        dbServiceAccount.getAccount(1);

        // изменение существующего
        account.setRest(new BigDecimal(252.5));
        dbServiceAccount.saveAccount(account);
        dbServiceAccount.getAccount(1);

        // получение несуществующего
        dbServiceAccount.getAccount(20);

    }
}
