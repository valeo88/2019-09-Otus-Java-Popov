package ru.otus.hw06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Реализация ячейки для банкнот одного номинала. */
public class BanknoteCellImpl implements BanknoteCell {
    // номинал ячейки
    private final Integer faceValue;
    // банкноты, хранящиеся в ячейке
    private List<Banknote> banknotes = new ArrayList<>();

    public BanknoteCellImpl(int faceValue) {
        this.faceValue = faceValue;
    }

    @Override
    public int faceValue() {
        return faceValue;
    }

    @Override
    public void add(Banknote banknote) {
        if (faceValue != banknote.faceValue()) throw new IllegalArgumentException("Not supported face value");
        banknotes.add(banknote);
    }

    @Override
    public Collection<Banknote> extract(int count) {
        if (count <= 0 || count > banknotes.size()) throw new IllegalArgumentException("Incorrect count of banknotes");
        Collection<Banknote> extracted = new ArrayList<>();
        while (count > 0) {
            extracted.add(banknotes.remove(banknotes.size()-1));
            count--;
        }
        return extracted;
    }

    @Override
    public int count() {
        return banknotes.size();
    }
}
