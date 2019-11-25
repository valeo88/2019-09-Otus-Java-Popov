package ru.otus.hw07.atm;

import ru.otus.hw07.atm.store.BanknotesStore;
import ru.otus.hw07.atm.store.BanknotesStoreImpl;

import java.util.Collection;

/** Реализация банкомата. */
public class ATMImpl implements ATM {
    private final int number;
    /** Хранилище банкнот в банкомате. */
    private BanknotesStore banknotesStore = new BanknotesStoreImpl();
    private ATMSnapshotService snapshotService = new ATMSnapshotService();

    public ATMImpl(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void load(Collection<Banknote> banknotes) {
        banknotes.forEach(banknote -> banknotesStore.put(banknote));
        saveState();
    }

    @Override
    public Collection<Banknote> cashOut(int amount) {
        if (amount <= 0) throw new ATMCashOutException("The amount of money can't be zero or negative.");
        Collection<Banknote> banknotes = banknotesStore.get(amount);
        if (banknotes.size() == 0) throw new ATMCashOutException("Can't cash out amount.");
        saveState();
        return banknotes;
    }

    @Override
    public void reset() {
        ATMState initialState = snapshotService.reset();
        this.banknotesStore = new BanknotesStoreImpl(initialState.getBanknoteStoreState().getCells());
    }

    @Override
    public int getBalance() {
        return banknotesStore.getBalance();
    }

    private void saveState() {
        snapshotService.saveState(new ATMState(banknotesStore.getState()));
    }

}
