package ru.otus.hw07.atm;

import ru.otus.hw07.atm.store.BanknoteStoreState;

/** Состояние банкомата характеризуется состоянием хранилища. */
public class ATMState {
    private final BanknoteStoreState banknoteStoreState;

    public ATMState(BanknoteStoreState banknoteStoreState) {
        this.banknoteStoreState = new BanknoteStoreState(banknoteStoreState);
    }

    public ATMState(ATMState state) {
        this.banknoteStoreState = new BanknoteStoreState(state.getBanknoteStoreState());
    }

    public BanknoteStoreState getBanknoteStoreState() {
        return banknoteStoreState;
    }
}
