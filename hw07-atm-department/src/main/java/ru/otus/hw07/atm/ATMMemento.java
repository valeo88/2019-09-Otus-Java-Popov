package ru.otus.hw07.atm;

/** Снимок состояния банкомата. */
public class ATMMemento {
    private final ATMState state;

    ATMMemento(ATMState state) {
        this.state = new ATMState(state);
    }

    ATMState getState() {
        return state;
    }
}
