package ru.otus.hw09.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.api.dao.AccountDao;
import ru.otus.hw09.api.dao.UserDaoException;
import ru.otus.hw09.api.model.Account;
import ru.otus.hw09.api.sessionmanager.SessionManager;
import ru.otus.hw09.jdbc.DbExecutor;
import ru.otus.hw09.jdbc.DbExecutorException;
import ru.otus.hw09.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class AccountDaoJdbc implements AccountDao {
    private static Logger logger = LoggerFactory.getLogger(AccountDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;

    public AccountDaoJdbc(SessionManagerJdbc sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<Account> findByNo(long no) {
        try {
            DbExecutor<Account> dbExecutor = getExecutor();
            return dbExecutor.load(no, Account.class);
        } catch (DbExecutorException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveAccount(Account account) {
        try {
            DbExecutor<Account> dbExecutor = getExecutor();
            dbExecutor.createOrUpdate(account);
            return account.getNo();
        } catch (DbExecutorException e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private DbExecutor<Account> getExecutor() {
        return new DbExecutor<>(sessionManager.getCurrentSession().getConnection(), Account.class);
    }
}
