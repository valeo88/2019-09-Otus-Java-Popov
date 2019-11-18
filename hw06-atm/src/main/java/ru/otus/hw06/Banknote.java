package ru.otus.hw06;

/** Доступные банкноты.*/
public enum Banknote {
    FIVE(5),
    TEN(10),
    TWENTY_FIVE(25),
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000);

    private final int faceValue;

    Banknote(int faceValue) {
        this.faceValue = faceValue;
    }

    public int faceValue() {
        return faceValue;
    }
}
