package ru.otus.hw09.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.api.dao.UserDao;
import ru.otus.hw09.api.dao.UserDaoException;
import ru.otus.hw09.api.model.User;
import ru.otus.hw09.api.sessionmanager.SessionManager;
import ru.otus.hw09.jdbc.DbExecutor;
import ru.otus.hw09.jdbc.DbExecutorException;
import ru.otus.hw09.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;

    public UserDaoJdbc(SessionManagerJdbc sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            DbExecutor<User> dbExecutor = getExecutor();
            return dbExecutor.load(id, User.class);
        } catch (DbExecutorException e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user) {
        try {
            DbExecutor<User> dbExecutor = getExecutor();
            dbExecutor.createOrUpdate(user);
            return user.getId();
        } catch (DbExecutorException e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private DbExecutor<User> getExecutor() {
        return new DbExecutor<>(sessionManager.getCurrentSession().getConnection(), User.class);
    }
}
