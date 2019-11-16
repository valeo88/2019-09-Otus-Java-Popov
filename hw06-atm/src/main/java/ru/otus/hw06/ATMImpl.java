package ru.otus.hw06;

import java.util.Collection;

/** Реализация банкомата. */
public class ATMImpl implements ATM {
    /** Хранилище банкнот в банкомате. */
    private BanknotesStore banknotesStore = new BanknotesStoreImpl();

    @Override
    public void load(Collection<Banknote> banknotes) {
        banknotes.forEach(banknote -> banknotesStore.put(banknote));
    }

    @Override
    public Collection<Banknote> cashOut(int amount) {
        if (amount <= 0) throw new ATMCashOutException("The amount of money can't be zero or negative.");
        Collection<Banknote> banknotes = banknotesStore.get(amount);
        if (banknotes.size() == 0) throw new ATMCashOutException("Can't cash out amount.");
        return banknotes;
    }

    @Override
    public int getBalance() {
        return banknotesStore.getBalance();
    }

}
