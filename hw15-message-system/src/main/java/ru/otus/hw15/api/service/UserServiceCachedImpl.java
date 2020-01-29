package ru.otus.hw15.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.hw15.api.cache.UserCache;
import ru.otus.hw15.api.model.User;
import ru.otus.hw15.api.repository.UserRepository;
import ru.otus.hw15.api.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceCachedImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceCachedImpl.class);

    private final UserRepository userRepository;
    private final UserCache cache;

    public UserServiceCachedImpl(UserRepository userRepository, UserCache cache) {
        this.userRepository = userRepository;
        this.cache = cache;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userRepository.saveUser(user);
                this.cache.add(user);
                sessionManager.commitSession();

                logger.info("saved user: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new UserServiceException(e);
            }
        }
    }


    @Override
    public Optional<User> getUser(long id) {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> mayBeUser = this.cache.get(id).or(() -> userRepository.findById(id));
                logger.info("loaded user: {}", mayBeUser.orElse(null));
                return mayBeUser;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                logger.error(e.getMessage(), e);
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUser(String login) {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userRepository.findByLogin(login);

                logger.info("loaded user by login: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                logger.error(e.getMessage(), e);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAll() {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            return userRepository.getAll();
        }
    }
}
