package ru.otus.hw07.atm.store;

import ru.otus.hw07.atm.Balance;
import ru.otus.hw07.atm.Banknote;

import java.util.Collection;

/** Хранилище банкнот. */
public interface BanknotesStore extends Balance {

    /** Поместить банкноту в хранилище. */
    void put(Banknote banknote);

    /** Выдача банкнот, если их можно выдать. */
    Collection<Banknote> get(int amount);

    /** Состояние хранилища. */
    BanknoteStoreState getState();


}
