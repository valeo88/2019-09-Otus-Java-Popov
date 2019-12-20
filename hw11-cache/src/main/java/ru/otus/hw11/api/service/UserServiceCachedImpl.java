package ru.otus.hw11.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.api.cache.UserCache;
import ru.otus.hw11.api.dao.UserDao;
import ru.otus.hw11.api.model.User;

import java.util.Optional;

public class UserServiceCachedImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceCachedImpl.class);

    private final UserDao userDao;
    private final UserCache cache;

    public UserServiceCachedImpl(UserDao userDao, UserCache cache) {
        this.userDao = userDao;
        this.cache = cache;
    }

    @Override
    public long saveUser(User user) {
        try (UserDao dao = this.userDao) {
            long userId = dao.saveUser(user);
            this.cache.add(user);

            logger.info("saved user: {}", userId);
            return userId;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserServiceException(e);
        }
    }


    @Override
    public Optional<User> getUser(long id) {
        try {
            Optional<User> cachedUser = this.cache.get(id);
            if (cachedUser.isPresent()) {
                logger.info("loaded user: {}", cachedUser);
                return cachedUser;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

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
