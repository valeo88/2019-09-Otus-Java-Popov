package ru.otus.hw07;

import java.util.ArrayList;
import java.util.List;

/** Производитель команд к банкоматам. */
public class ATMDepartmentCommandProducer {
    private final List<ATMDepartmentCommandListener> listeners = new ArrayList<>();

    public void addListener(ATMDepartmentCommandListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ATMDepartmentCommandListener listener) {
        listeners.remove(listener);
    }

    /** Сброс слушателей. */
    public void reset() {
        listeners.forEach(ATMDepartmentCommandListener::onReset);
    }

    /** Сбор баланса со всех слушателей. */
    public int collectBalance() {
        int balance = 0;
        for (ATMDepartmentCommandListener listener : listeners) {
            balance += listener.onGetBalance();
        }
        return balance;
    }

}
