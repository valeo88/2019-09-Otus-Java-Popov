package ru.otus.hw07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/** Реализация департамента АТМ, достаточная для решения задачи. */
public class ATMDepartmentImpl implements ATMDepartment {
    private final List<ATM> atms = new ArrayList<>();

    public ATMDepartmentImpl(Collection<ATM> atms) {
        this.atms.addAll(atms);
    }

    @Override
    public ATM find(int number) {
        Optional<ATM> optionalATM = atms.stream().filter(atm -> atm.getNumber() == number).findFirst();
        return optionalATM.orElse(null);
    }

    @Override
    public void resetATMs() {
        /* можно считать что департамент выступает в роли генератора события,
         а банкоматы являются подписчиками, поэтому это похоже на паттерн Observer.
         */
        atms.forEach(ATM::reset);
    }

    @Override
    public int getBalance() {
        int balance = 0;
        for (ATM atm : atms) {
            balance += atm.getBalance();
        }
        return balance;
    }
}
