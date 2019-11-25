package ru.otus.hw07.atm;

import java.util.ArrayDeque;
import java.util.Deque;

/** Снимки состояния банкомата. */
public class ATMSnapshotService {
    private Deque<ATMMemento> mementos = new ArrayDeque<>();

    /** Сохранение состояния. */
    public void saveState(ATMState state) {
        mementos.push(new ATMMemento(state));
    }

    /** Получение первоначального состояния и сброс всех состояний. */
    public ATMState reset() {
        ATMState state = mementos.getLast().getState();
        this.mementos = new ArrayDeque<>();
        saveState(state);
        return state;
    }


}
