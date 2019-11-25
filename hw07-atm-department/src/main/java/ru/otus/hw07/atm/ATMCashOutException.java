package ru.otus.hw07.atm;

/** Ошибка при выдаче денег банкоматом. */
public class ATMCashOutException extends RuntimeException {

    public ATMCashOutException(String message) {
        super(message);
    }
}
