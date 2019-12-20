package ru.otus.hw11.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw11.api.cache.UserCache;
import ru.otus.hw11.api.model.User;

import java.util.Optional;

/** Реализация UserCache на основе MyCache. */
public class UserCacheImpl implements UserCache {
    private static final Logger logger = LoggerFactory.getLogger(UserCacheImpl.class);

    private final HwCache<String, User> cache = new MyCache<>();

    @Override
    public void add(User user) {
        this.cache.put(getKey(user.getId()), user);
        logger.debug("{} added to cache", user);
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(this.cache.get(getKey(id)));
    }

    private String getKey(long id) {
        return String.format("User_%d", id);
    }
}
