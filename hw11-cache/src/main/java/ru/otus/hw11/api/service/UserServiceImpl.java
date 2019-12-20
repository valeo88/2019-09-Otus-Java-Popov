package ru.otus.hw11.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.api.dao.UserDao;
import ru.otus.hw11.api.model.User;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public long saveUser(User user) {
        try (UserDao dao = this.userDao) {
            long userId = dao.saveUser(user);

            logger.info("saved user: {}", userId);
            return userId;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserServiceException(e);
        }
    }


    @Override
    public Optional<User> getUser(long id) {
        try (UserDao dao = this.userDao) {
            Optional<User> userOptional = dao.findById(id);

            logger.info("loaded user: {}", userOptional.orElse(null));
            return userOptional;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

}
