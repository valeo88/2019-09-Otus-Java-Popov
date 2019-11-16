package ru.otus.hw06;

import java.util.Collection;

/** Хранилище банкнот. */
public interface BanknotesStore {

    /** Поместить банкноту в хранилище. */
    void put(Banknote banknote);

    /** Выдача банкнот, если их можно выдать. */
    Collection<Banknote> get(int amount);

    /** Баланс в хранилище. */
    int getBalance();
}
