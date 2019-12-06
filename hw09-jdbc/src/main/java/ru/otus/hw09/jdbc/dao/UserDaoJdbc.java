package ru.otus.hw09.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.api.dao.UserDao;
import ru.otus.hw09.api.dao.UserDaoException;
import ru.otus.hw09.api.model.User;
import ru.otus.hw09.api.sessionmanager.SessionManager;
import ru.otus.hw09.jdbc.DbExecutor;
import ru.otus.hw09.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class UserDaoJdbc implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<User> dbExecutor;

    public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            return dbExecutor.load(id, User.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user) {
        try {
            if (dbExecutor.load(user.getId(), User.class).isPresent()) {
                dbExecutor.update(user);
            } else {
                dbExecutor.create(user);
            }
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
