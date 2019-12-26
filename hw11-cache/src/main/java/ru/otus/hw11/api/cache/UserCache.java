package ru.otus.hw11.api.cache;

import ru.otus.hw11.api.model.User;
import ru.otus.hw11.cachehw.HwCacheListener;

import java.util.Optional;

public interface UserCache {
    void add(User user);

    Optional<User> get(long id);

    void remove(User user);

    void addListener(HwCacheListener<String, User> listener);

    void removeListener(HwCacheListener<String, User> listener);
}
