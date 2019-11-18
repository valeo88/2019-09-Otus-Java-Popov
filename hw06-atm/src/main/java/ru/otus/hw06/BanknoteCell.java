package ru.otus.hw06;

import java.util.Collection;

/** Ячейка с банкнотами одного номинала. */
public interface BanknoteCell {

    /** Номинал ячейки. */
    int faceValue();

    /** Добавление банкноты в ячейку. */
    void add(Banknote banknote);

    /** Извлечение банкнот из ячейки. */
    Collection<Banknote> extract(int count);

    /** Подсчет количества банкнот в ячейке. */
    int count();
}
