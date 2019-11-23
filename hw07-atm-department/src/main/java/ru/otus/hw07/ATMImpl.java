package ru.otus.hw07;

import java.util.ArrayDeque;
import java.util.Deque;

/** Минимальная реализация банкомата, достаточная для решения задачи.
 * Для хранения состояния используется паттерн Memento. */
public class ATMImpl implements ATM {
    private final int number;
    // текущее состояние банкомата
    private ATMState state;
    // хранилище состояний банкомата
    private Deque<ATMMemento> atmMementos = new ArrayDeque<>();

    public ATMImpl(int number, int initialBalance) {
        if (initialBalance < 0) throw new IllegalArgumentException("Balance can't be less 0.");
        this.number = number;
        this.state = new ATMState(initialBalance);
        saveState();
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int cashOut(int amount) {
        if (amount > state.getBalance()) throw new IllegalArgumentException("ATM has't such amount.");
        updateState(state.getBalance() - amount);
        return amount;
    }

    @Override
    public void reset() {
        this.state = atmMementos.getLast().getState();
        this.atmMementos = new ArrayDeque<>();
        saveState();
    }

    @Override
    public int getBalance() {
        return state.getBalance();
    }

    private void updateState(int newBalance) {
        this.state = new ATMState(newBalance);
        saveState();
    }

    private void saveState() {
        atmMementos.push(new ATMMemento(this.state));
    }

}
