package ru.otus.hw11.api.cache;

import ru.otus.hw11.api.model.User;
import ru.otus.hw11.cachehw.HwCacheAction;

public interface UserCacheListener {
    void notify(User user, HwCacheAction action);
}
