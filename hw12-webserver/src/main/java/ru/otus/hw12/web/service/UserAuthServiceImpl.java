package ru.otus.hw12.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12.api.dao.UserDao;
import ru.otus.hw12.api.sessionmanager.SessionManager;

public class UserAuthServiceImpl implements UserAuthService {
    private final static Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    private final UserDao userDao;

    public UserAuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        boolean result = false;
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                result = userDao.findByLogin(login)
                        .map(user -> user.getPassword().equals(password))
                        .orElse(false);
            } catch (Exception e) {
                sessionManager.rollbackSession();
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

}
