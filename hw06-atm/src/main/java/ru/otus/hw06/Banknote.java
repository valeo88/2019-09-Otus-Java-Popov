package ru.otus.hw06;

/** Банкнота. Номинал банкноты устанавливается при создании и не меняется.
 * Чтобы исключить возможность создания банкнот произвольных номиналов используется перечисление с доступными номиналами.*/
public class Banknote {
    private final BanknoteFaceValue faceValue;

    public Banknote(BanknoteFaceValue faceValue) {
        this.faceValue = faceValue;
    }

    public BanknoteFaceValue faceValue() {
        return faceValue;
    }

}
