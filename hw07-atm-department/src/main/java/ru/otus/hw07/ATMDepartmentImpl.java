package ru.otus.hw07;

import ru.otus.hw07.atm.ATM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/** Реализация департамента АТМ. */
public class ATMDepartmentImpl implements ATMDepartment {
    private final List<ATM> atms = new ArrayList<>();
    private final ATMDepartmentCommandProducer commandProducer = new ATMDepartmentCommandProducer();

    public ATMDepartmentImpl(Collection<ATM> atms) {
        this.atms.addAll(atms);
        this.atms.forEach(commandProducer::addListener);
    }

    @Override
    public ATM find(int number) {
        Optional<ATM> optionalATM = atms.stream().filter(atm -> atm.getNumber() == number).findFirst();
        return optionalATM.orElse(null);
    }

    @Override
    public void resetATMs() {
        commandProducer.reset();
    }

    @Override
    public int collectBalance() {
        return commandProducer.collectBalance();
    }
}
