package ru.otus.hw06;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/** Хранилище банкнот в банкомате. Содержит в себе ячейки для хранения банкнот разных номиналов. */
public class BanknotesStore {
    // для простоты считаем, что на каждый номинал может быть только одна ячейка
    private SortedMap<BanknoteFaceValue, BanknoteCell> store = new TreeMap<>(Comparator.comparing(BanknoteFaceValue::value,
            Comparator.reverseOrder()));

    public BanknotesStore() {
        // сразу создаем ячейки под все доступные номиналы
        for (BanknoteFaceValue faceValue: BanknoteFaceValue.values()) {
            store.put(faceValue, new BanknoteCell(faceValue));
        }
    }

    /** Поместить банкноту в хранилище. */
    public void put(Banknote banknote) {
        store.get(banknote.faceValue()).add(banknote);
    }

    /** Выдача банкнот, если их можно выдать. */
    public Collection<Banknote> get(int amount) {
        Collection<Banknote> banknotes = new ArrayList<>();
        if (canGet(amount)) {
            for (BanknoteCell banknoteCell : store.values()) {
                int cnt = Math.min(banknoteCell.count(), amount / banknoteCell.faceValue().value());
                if (cnt == 0) continue;
                banknotes.addAll(banknoteCell.extract(cnt));
                amount -= cnt * banknoteCell.faceValue().value();
                if (amount == 0) break;
            }
        }
        return banknotes;
    }

    /** Баланс в хранилище. */
    public int getBalance() {
        AtomicInteger balance = new AtomicInteger();
        store.forEach(((faceValue, banknoteCell)
                -> balance.addAndGet(banknoteCell.count() * banknoteCell.faceValue().value())));
        return balance.get();
    }

    /** Проверка возможности выдачи запрашиваемой сумммы.*/
    private boolean canGet(int amount) {
        // используем жадный алгоритм, идем в порядке убывания номинала
        for (BanknoteCell banknoteCell : store.values()) {
            int cnt = Math.min(banknoteCell.count(), amount / banknoteCell.faceValue().value());
            amount -= cnt * banknoteCell.faceValue().value();
            if (amount == 0) return true;
        }
        return false;
    }
}
