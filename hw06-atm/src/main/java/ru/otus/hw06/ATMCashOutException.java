package ru.otus.hw06;

/** Ошибка при выдаче денег банкоматом. */
public class ATMCashOutException extends RuntimeException {

    public ATMCashOutException(String message) {
        super(message);
    }
}
