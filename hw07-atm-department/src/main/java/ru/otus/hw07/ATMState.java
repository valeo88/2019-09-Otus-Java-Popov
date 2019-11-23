package ru.otus.hw07;

/** Состояние банкомата характеризуется балансом. */
public class ATMState {
    private final int balance;

    public ATMState(int balance) {
        this.balance = balance;
    }

    public ATMState(ATMState state) {
        this.balance = state.getBalance();
    }

    public int getBalance() {
        return balance;
    }
}
