package ru.otus.hw07.atm.store;

import ru.otus.hw07.atm.Banknote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** Состояние хранилища банкомата. */
public class BanknoteStoreState {
    private final List<BanknoteCell> cells = new ArrayList<>();

    public BanknoteStoreState(BanknoteStoreState state) {
        copy(state.getCells());
    }

    public BanknoteStoreState(Collection<BanknoteCell> cells) {
        copy(cells);
    }

    public Collection<BanknoteCell> getCells() {
        return cells;
    }

    private void copy(Collection<BanknoteCell> cells) {
        for (BanknoteCell banknoteCell: cells) {
            Banknote banknote = Banknote.valueOf(banknoteCell.faceValue());
            BanknoteCell bc = new BanknoteCellImpl(banknote);
            for (int i = 0; i < banknoteCell.count(); i++) {
                bc.add(banknote);
            }
            this.cells.add(bc);
        }
    }
}
