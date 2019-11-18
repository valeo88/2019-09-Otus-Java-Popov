package ru.otus.hw06;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/** Хранилище банкнот в банкомате. Содержит в себе ячейки для хранения банкнот разных номиналов. */
public class BanknotesStoreImpl implements BanknotesStore {
    // для простоты считаем, что на каждый номинал может быть только одна ячейка
    private SortedMap<Integer, BanknoteCell> store = new TreeMap<>(Comparator.comparing(Integer::intValue,
            Comparator.reverseOrder()));

    public BanknotesStoreImpl() {
        // сразу создаем ячейки под все доступные номиналы
        for (Banknote banknote: Banknote.values()) {
            store.put(banknote.faceValue(), new BanknoteCellImpl(banknote));
        }
    }

    @Override
    public void put(Banknote banknote) {
        store.get(banknote.faceValue()).add(banknote);
    }

    @Override
    public Collection<Banknote> get(int amount) {
        Collection<Banknote> banknotes = new ArrayList<>();
        if (canGet(amount)) {
            for (BanknoteCell banknoteCell : store.values()) {
                int cnt = Math.min(banknoteCell.count(), amount / banknoteCell.faceValue());
                if (cnt == 0) continue;
                banknotes.addAll(banknoteCell.extract(cnt));
                amount -= cnt * banknoteCell.faceValue();
                if (amount == 0) break;
            }
        }
        return banknotes;
    }

    @Override
    public int getBalance() {
        AtomicInteger balance = new AtomicInteger();
        store.forEach(((faceValue, banknoteCell)
                -> balance.addAndGet(banknoteCell.count() * banknoteCell.faceValue())));
        return balance.get();
    }

    /** Проверка возможности выдачи запрашиваемой сумммы.*/
    private boolean canGet(int amount) {
        // используем жадный алгоритм, идем в порядке убывания номинала
        for (BanknoteCell banknoteCell : store.values()) {
            int cnt = Math.min(banknoteCell.count(), amount / banknoteCell.faceValue());
            amount -= cnt * banknoteCell.faceValue();
            if (amount == 0) return true;
        }
        return false;
    }
}
