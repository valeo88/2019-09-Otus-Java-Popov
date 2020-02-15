package ru.otus.hw15.cachehw;

/**
 * @author sergey
 * created on 14.12.18.
 */
public interface HwCache<K, V> {
    void put(K key, V value);

    void remove(K key);

    V get(K key);

    void addListener(HwCacheListener<K, V> listener);

    void removeListener(HwCacheListener<K, V> listener);
}
