package ru.otus.hw07.atm;

import java.util.HashMap;
import java.util.Map;

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
    private static final Map<Integer, Banknote> BY_VALUE = new HashMap<>();

    static {
        for (Banknote e: values()) {
            BY_VALUE.put(e.faceValue, e);
        }
    }

    Banknote(int faceValue) {
        this.faceValue = faceValue;
    }

    public int faceValue() {
        return faceValue;
    }

    public static Banknote valueOf(int faceValue) {
        return BY_VALUE.get(faceValue);
    }
}
