package ru.otus.hw06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Ячейка для банкнот одного номинала. */
public class BanknoteCell {
    // номинал ячейки
    private BanknoteFaceValue faceValue;
    // банкноты, хранящиеся в ячейке
    private List<Banknote> banknotes = new ArrayList<>();

    public BanknoteCell(BanknoteFaceValue faceValue) {
        this.faceValue = faceValue;
    }

    public BanknoteFaceValue faceValue() {
        return faceValue;
    }

    /** Добавление банкноты в ячейку. */
    public void add(Banknote banknote) {
        if (faceValue != banknote.faceValue()) throw new IllegalArgumentException("Not supported face value");
        banknotes.add(banknote);
    }

    /** Извлечение банкнот из ячейки. */
    public Collection<Banknote> extract(int count) {
        if (count <= 0 || count > banknotes.size()) throw new IllegalArgumentException("Incorrect count of banknotes");
        Collection<Banknote> extracted = new ArrayList<>();
        while (count > 0) {
            extracted.add(banknotes.remove(banknotes.size()-1));
            count--;
        }
        return extracted;
    }

    /** Количество банкнот в ячейке. */
    public int count() {
        return banknotes.size();
    }
}
