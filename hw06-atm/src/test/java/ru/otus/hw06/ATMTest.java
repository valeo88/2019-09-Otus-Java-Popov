package ru.otus.hw06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тестирование функциональности банкомата.")
class ATMTest {

    private ATM atm;
    private int initialBalance;
    private Banknote[] banknotes = {Banknote.TEN,
                                                Banknote.TWENTY_FIVE,
                                                Banknote.TEN,
                                                Banknote.TWENTY_FIVE,
                                                Banknote.FIFTY,
                                                Banknote.ONE_HUNDRED,
                                                Banknote.TWENTY_FIVE,
                                                Banknote.TEN,
                                                Banknote.ONE_HUNDRED,
                                                Banknote.FIFTY,
                                                Banknote.TEN,
                                                Banknote.FIVE,
                                                Banknote.TEN,
                                                Banknote.FIVE};

    @BeforeEach
    void before() {
        atm = new ATMImpl();

        Collection<Banknote> banknotes = new ArrayList<>();
        for (Banknote banknote : this.banknotes) {
            banknotes.add(banknote);
            initialBalance += banknote.faceValue();
        }
        atm.load(banknotes);
    }

    @DisplayName("Загрузка банкнот")
    @Test
    void load() {
        List<Banknote> banknotesToLoad = List.of(Banknote.FIVE, Banknote.ONE_HUNDRED);

        atm.load(banknotesToLoad);

        assertEquals(initialBalance + 105, atm.getBalance());
    }

    @DisplayName("Выдача банкнот - минимальная корректная сумма")
    @Test
    void cashOutMinCorrect() {
        int amount = 5;

        Collection<Banknote> b1 = atm.cashOut(amount);

        assertEquals(1, b1.size());
        assertEquals(amount, (int) b1.stream().map(Banknote::faceValue).reduce(Integer::sum).get());
    }

    @DisplayName("Выдача банкнот - максимальная корректная сумма")
    @Test
    void cashOutMaxCorrect() {
        int amount  = initialBalance;

        Collection<Banknote> b2 = atm.cashOut(amount);

        assertEquals(banknotes.length, b2.size());
        assertEquals(amount, (int) b2.stream().map(Banknote::faceValue).reduce(Integer::sum).get());
    }

    @DisplayName("Выдача банкнот - корректная промежуточная сумма")
    @Test
    void cashCorrect() {
        int amount  = 80;

        Collection<Banknote> b2 = atm.cashOut(amount);

        assertEquals(3, b2.size());
        assertEquals(amount, (int) b2.stream().map(Banknote::faceValue).reduce(Integer::sum).get());
    }

    @DisplayName("Выдача банкнот - отрицательная сумма")
    @Test
    void cashOutNegative() {
        int amount = -1;

        assertThrows(ATMCashOutException.class, () -> atm.cashOut(amount));
    }

    @DisplayName("Выдача банкнот - некорректная сумма")
    @Test
    void cashIncorrect() {
        int amount  = 47;

        assertThrows(ATMCashOutException.class, () -> atm.cashOut(amount));
    }

    @DisplayName("Получение баланса")
    @Test
    void getBalance() {
        long balance = atm.getBalance();

        assertEquals(initialBalance, balance);
    }
}