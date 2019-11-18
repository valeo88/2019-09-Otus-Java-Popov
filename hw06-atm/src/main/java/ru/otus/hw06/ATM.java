package ru.otus.hw06;

import java.util.Collection;

/** Банкомат. */
public interface ATM {

    /** Загрузка банкнот в банкомат. */
    void load(Collection<Banknote> banknotes);

    /** Выдача минимального количества банкнот по запрошенной сумме. */
    Collection<Banknote> cashOut(int amount);

    /** Выдача суммы остатка денежных средств в банкомате. */
    int getBalance();
}
