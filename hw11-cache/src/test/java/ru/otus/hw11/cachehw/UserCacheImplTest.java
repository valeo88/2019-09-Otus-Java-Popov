package ru.otus.hw11.cachehw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw11.api.cache.UserCache;
import ru.otus.hw11.api.model.AddressDataSet;
import ru.otus.hw11.api.model.User;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Кэш пользователей должен ")
public class UserCacheImplTest {

    private UserCache cache;
    private User user;

    @BeforeEach
    void setUp() {
        cache = new UserCacheImpl();

        user = new User("Ivan");
        user.setAddress(new AddressDataSet("Lenina"));
    }

    @Test
    @DisplayName(" добавлять пользователя в кэш и извлекать из кэша.")
    void shouldAddUserToCacheAndGet() {
        cache.add(user);

        Optional<User> mayBeUser = cache.get(user.getId());
        assertThat(mayBeUser).isPresent();
        assertThat(mayBeUser).get().isEqualToComparingFieldByField(user);
    }

    @Test
    @DisplayName(" добавлять пользователя в кэш и очищаться после GC.")
    void shouldAddUserToCache() throws InterruptedException {
        cache.add(user);

        System.gc();
        Thread.sleep(500);

        Optional<User> mayBeUser = cache.get(user.getId());
        assertThat(mayBeUser).isNotPresent();
    }

    @Test
    @DisplayName(" удалять пользователя из кэша.")
    void shouldRemoveUserFromCache() {
        cache.add(user);

        cache.remove(user);

        Optional<User> mayBeUser = cache.get(user.getId());
        assertThat(mayBeUser).isNotPresent();
    }

    @Test
    @DisplayName(" добавлять UserCacheListener и вызывать его при добавлении/удаления пользователя.")
    void shouldAddUserCacheListenerAndNotifyIt() {
        AtomicReference<User> userFromListener = new AtomicReference<>();
        AtomicReference<HwCacheAction> actionFromListener = new AtomicReference<>();
        HwCacheListener<String, User> listener = (String key, User user, HwCacheAction action) -> {
            userFromListener.set(user);
            actionFromListener.set(action);
        };
        cache.addListener(listener);

        cache.add(user);

        assertThat(userFromListener).hasValue(user);
        assertThat(actionFromListener).hasValue(HwCacheAction.ADD_TO_CACHE);

        cache.remove(user);

        assertThat(userFromListener).hasValue(user);
        assertThat(actionFromListener).hasValue(HwCacheAction.REMOVE_FROM_CACHE);

        cache.removeListener(listener);
    }
}
