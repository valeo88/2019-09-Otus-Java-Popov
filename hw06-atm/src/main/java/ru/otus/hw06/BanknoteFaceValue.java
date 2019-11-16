package ru.otus.hw06;

/** Доступные номиналы банкнот.*/
public enum BanknoteFaceValue {
    FIVE(5),
    TEN(10),
    TWENTY_FIVE(25),
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000);

    private final int value;

    BanknoteFaceValue(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
