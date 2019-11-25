package ru.otus.hw07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw07.atm.ATM;
import ru.otus.hw07.atm.ATMImpl;
import ru.otus.hw07.atm.Banknote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование департамента банкоматов. ")
public class ATMDepartmentTest {

    private List<ATM> atms;
    private ATMDepartment atmDepartment;

    @BeforeEach
    void beforeEach() {
        atms = new ArrayList<>();

        ATM atm1 = new ATMImpl(1);
        atm1.load(Arrays.asList(Banknote.FIVE, Banknote.ONE_HUNDRED, Banknote.TWENTY_FIVE));

        ATM atm2 = new ATMImpl(2);
        atm2.load(Arrays.asList(Banknote.FIVE_HUNDRED, Banknote.FIFTY, Banknote.TEN));

        ATM atm3 = new ATMImpl(3);
        atm3.load(Arrays.asList(Banknote.ONE_HUNDRED, Banknote.FIFTY, Banknote.TEN));

        atms.add(atm1);
        atms.add(atm2);
        atms.add(atm3);

        atmDepartment = new ATMDepartmentImpl(atms);
    }

    @DisplayName("Поиск банкомата по номеру.")
    @Test
    void find() {
        assertNotNull(atmDepartment.find(1));
        assertNull(atmDepartment.find(981));
    }

    @DisplayName("Сброс состояния банкоматов.")
    @Test
    void resetATMs() {
        // изменение балансов
        int initialSum = atmDepartment.getBalance();

        atmDepartment.find(1).cashOut(5);
        atmDepartment.find(2).cashOut(10);

        assertEquals(initialSum - 15, atmDepartment.getBalance());

        // cброс до начального состояния и проверка
        atmDepartment.resetATMs();

        assertEquals(initialSum, atmDepartment.getBalance());
    }

    @DisplayName("Получение баланса со всех банкоматов департамента.")
    @Test
    void getBalance() {
        int sumFromATMs = atms.stream().map(ATM::getBalance).reduce(Integer::sum).get();

        int sumFromDept = atmDepartment.getBalance();

        assertEquals(sumFromATMs, sumFromDept);
    }
}
