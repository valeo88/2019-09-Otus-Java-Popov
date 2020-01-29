package ru.otus.hw15.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    private final Map<K, V> data = new WeakHashMap<>();
    private final List<HwCacheListener<K,V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        data.put(key, value);
        notifyListeners(key, value, HwCacheAction.ADD_TO_CACHE);
    }

    @Override
    public void remove(K key) {
        final V value = data.remove(key);
        notifyListeners(key, value, HwCacheAction.REMOVE_FROM_CACHE);
    }

    @Override
    public V get(K key) {
        return data.get(key);
    }

    @Override
    public void addListener(HwCacheListener<K,V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwCacheListener<K,V> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(K key, V value, HwCacheAction action) {
        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                logger.error("Error on listener {} notification (key: {}, value: {}, action: {}): ",
                        listener, key, value, action, e);
            }
        });
    }
}
