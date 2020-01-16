package ru.otus.hw13.cachehw;

/** Действия с кэшом. */
public enum HwCacheAction {
    ADD_TO_CACHE("add"),
    REMOVE_FROM_CACHE("remove");

    private final String value;

    HwCacheAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
