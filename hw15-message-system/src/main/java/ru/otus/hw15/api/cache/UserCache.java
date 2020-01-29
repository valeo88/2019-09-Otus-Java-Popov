package ru.otus.hw15.api.cache;

import ru.otus.hw15.api.model.User;
import ru.otus.hw15.cachehw.HwCacheListener;

import java.util.Optional;

public interface UserCache {
    String NAME = "userCache";

    void add(User user);

    Optional<User> get(long id);

    void remove(User user);

    void addListener(HwCacheListener<String, User> listener);

    void removeListener(HwCacheListener<String, User> listener);
}
