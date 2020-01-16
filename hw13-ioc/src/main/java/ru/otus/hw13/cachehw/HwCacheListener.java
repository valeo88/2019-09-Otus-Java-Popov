package ru.otus.hw13.cachehw;

/**
 * @author sergey
 * created on 14.12.18.
 */
public interface HwCacheListener<K, V> {
    void notify(K key, V value, HwCacheAction action);
}
