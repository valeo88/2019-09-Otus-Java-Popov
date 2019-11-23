package ru.otus.hw07;

/** Банкомат. */
public interface ATM {

    /** Номер данного банкомата.*/
    int getNumber();

    /** Выдать деньги. */
    int cashOut(int amount);

    /** Сброс состояния банкомата до начального. */
    void reset();

    /** Получение остатка денежных средств. */
    int getBalance();
}
