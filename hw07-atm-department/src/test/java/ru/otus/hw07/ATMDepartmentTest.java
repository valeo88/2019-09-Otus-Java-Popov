package ru.otus.hw07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование департамента банкоматов. ")
public class ATMDepartmentTest {

    private List<ATM> atms;
    private ATMDepartment atmDepartment;

    @BeforeEach
    void beforeEach() {
        atms = new ArrayList<>();
        atms.add(new ATMImpl(1, 100));
        atms.add(new ATMImpl(2, 76));
        atms.add(new ATMImpl(3, 521));
        atms.add(new ATMImpl(4, 1001));

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
        int diff = 50;

        atmDepartment.find(1).cashOut(diff);
        atmDepartment.find(2).cashOut(diff);

        assertEquals(initialSum - 2 * diff, atmDepartment.getBalance());

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
