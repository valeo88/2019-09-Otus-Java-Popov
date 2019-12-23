package ru.otus.hw11.api.cache;

import ru.otus.hw11.api.model.User;

import java.util.Optional;

public interface UserCache {
    void add(User user);

    Optional<User> get(long id);

    void remove(User user);

    void addListener(UserCacheListener listener);

    void removeListener(UserCacheListener listener);
}
