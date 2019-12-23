package ru.otus.hw11.cachehw;

import ru.otus.hw11.api.cache.UserCache;
import ru.otus.hw11.api.cache.UserCacheListener;
import ru.otus.hw11.api.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/** Реализация UserCache на основе MyCache. */
public class UserCacheImpl implements UserCache {

    private final HwCache<String, User> cache = new MyCache<>();
    private final Map<UserCacheListener, HwCacheListener<String, User>> listenersMap = new HashMap<>();

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
    public void addListener(UserCacheListener listener) {
        HwCacheListener<String, User> hwCacheListener = (String key, User value, HwCacheAction action) -> {
            listener.notify(value, action);
        };
        cache.addListener(hwCacheListener);
        listenersMap.put(listener, hwCacheListener);
    }

    @Override
    public void removeListener(UserCacheListener listener) {
        cache.removeListener(listenersMap.get(listener));
        listenersMap.remove(listener);
    }

    private String getKey(long id) {
        return String.format("User_%d", id);
    }
}
