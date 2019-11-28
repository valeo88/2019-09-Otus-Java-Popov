package ru.otus.hw07.atm.store;

import ru.otus.hw07.atm.Balance;
import ru.otus.hw07.atm.Banknote;

import java.util.Collection;

/** Ячейка с банкнотами одного номинала. */
public interface BanknoteCell extends Balance {

    /** Номинал ячейки. */
    int faceValue();

    /** Добавление банкноты в ячейку. */
    void add(Banknote banknote);

    /** Извлечение банкнот из ячейки. */
    Collection<Banknote> extract(int count);

    /** Подсчет количества банкнот в ячейке. */
    int count();
}
