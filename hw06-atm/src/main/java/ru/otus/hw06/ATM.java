package ru.otus.hw06;

import java.util.Collection;

/** Банкомат. */
public class ATM {
    /** Хранилище банкнот в банкомате. */
    private BanknotesStore banknotesStore = new BanknotesStore();

    /** Загрузка банкнот в банкомат. */
    public void load(Collection<Banknote> banknotes) {
        banknotes.forEach(banknote -> banknotesStore.put(banknote));
    }

    /** Выдача минимального количества банкнот по запрошенной сумме. */
    public Collection<Banknote> cashOut(int amount) {
        if (amount <= 0) throw new ATMCashOutException("The amount of money can't be zero or negative.");
        Collection<Banknote> banknotes = banknotesStore.get(amount);
        if (banknotes.size() == 0) throw new ATMCashOutException("Can't cash out amount.");
        return banknotes;
    }

    /** Выдать сумму остатка денежных средств в банкомате. */
    public int getBalance() {
        return banknotesStore.getBalance();
    }

}
