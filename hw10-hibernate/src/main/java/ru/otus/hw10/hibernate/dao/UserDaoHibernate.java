package ru.otus.hw10.hibernate.dao;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw10.api.dao.UserDao;
import ru.otus.hw10.api.dao.UserDaoException;
import ru.otus.hw10.api.model.User;
import ru.otus.hw10.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class UserDaoHibernate implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public UserDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<User> findById(long id) {
        sessionManager.beginSession();
        try {
            return Optional.ofNullable(sessionManager.getCurrentSession().getHibernateSession().find(User.class, id));
        } catch (Exception e) {
            sessionManager.rollbackSession();
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long saveUser(User user) {
        if (user==null) throw new IllegalArgumentException("User is null");

        sessionManager.beginSession();
        try {
            Session hibernateSession = sessionManager.getCurrentSession().getHibernateSession();
            if (user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                hibernateSession.persist(user);
            }
            sessionManager.commitSession();
            return user.getId();
        } catch (Exception e) {
            sessionManager.rollbackSession();
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }


    @Override
    public void close() throws Exception {
        sessionManager.close();
    }
}
