package ru.otus.hw11.cachehw;

import ru.otus.hw11.api.cache.UserCache;
import ru.otus.hw11.api.model.User;

import java.util.Optional;

/** Реализация UserCache на основе MyCache. */
public class UserCacheImpl implements UserCache {

    private final HwCache<String, User> cache = new MyCache<>();

    @Override
    public void add(User user) {
        cache.put(getKey(user.getId()), user);
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(cache.get(getKey(id)));
    }

    @Override
    public void remove(User user) {
        cache.remove(getKey(user.getId()));
    }

    @Override
    public void addListener(HwCacheListener<String, User> listener) {
        cache.addListener(listener);
    }

    @Override
    public void removeListener(HwCacheListener<String, User> listener) {
        cache.removeListener(listener);
    }

    private String getKey(long id) {
        return String.format("User_%d", id);
    }
}
