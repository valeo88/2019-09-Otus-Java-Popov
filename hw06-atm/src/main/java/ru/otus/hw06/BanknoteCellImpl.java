package ru.otus.hw06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Реализация ячейки для банкнот одного номинала. */
public class BanknoteCellImpl implements BanknoteCell {
    // банкноты, для которых предназначена ячейка
    private final Banknote banknote;
    // банкноты, хранящиеся в ячейке
    private List<Banknote> banknotes = new ArrayList<>();

    public BanknoteCellImpl(Banknote banknote) {
        this.banknote = banknote;
    }

    @Override
    public int faceValue() {
        return banknote.faceValue();
    }

    @Override
    public void add(Banknote banknote) {
        if (this.banknote.faceValue() != banknote.faceValue()) throw new IllegalArgumentException("Not supported face value");
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
