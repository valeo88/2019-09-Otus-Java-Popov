package ru.otus.hw09.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw09.api.dao.AccountDao;
import ru.otus.hw09.api.model.Account;
import ru.otus.hw09.h2.DataSourceH2;
import ru.otus.hw09.h2.mappers.AccountMapper;
import ru.otus.hw09.jdbc.dao.AccountDaoJdbc;
import ru.otus.hw09.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты сервиса счетов. ")
class DBServiceAccountTest {

    private DataSource dataSource;
    private SessionManagerJdbc sessionManager;
    private AccountDao accountDao;
    private DBServiceAccount dbServiceAccount;

    private Account account;

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = new DataSourceH2();
        sessionManager = new SessionManagerJdbc(dataSource);

        new AccountMapper(dataSource).createTable();

        accountDao = new AccountDaoJdbc(sessionManager);
        dbServiceAccount = new DbServiceAccountImpl(accountDao);

        account = new Account();
        account.setType("First");
        account.setRest(new BigDecimal(100.1));
    }

    @Test
    @DisplayName("Сохранение данных счета в БД если он не существует.")
    void saveAccountIfNotExists() {
        long no = dbServiceAccount.saveAccount(account);

        assertEquals(account.getNo(), no);
    }

    @Test
    @DisplayName("Сохранение данных счета в БД если он существует.")
    void saveAccountIfExists() {
        dbServiceAccount.saveAccount(account);

        account.setRest(new BigDecimal(1000));
        dbServiceAccount.saveAccount(account);
        Account loaded = dbServiceAccount.getAccount(account.getNo()).get();

        assertEquals(account, loaded);

    }

    @Test
    @DisplayName("Получение данных счета из БД.")
    void getAccount() {
        dbServiceAccount.saveAccount(account);
        Account loaded = dbServiceAccount.getAccount(account.getNo()).get();

        assertEquals(account, loaded);

        Optional<Account> notExists = dbServiceAccount.getAccount(100);
        assertEquals(Optional.empty(), notExists);
    }
}