package ru.otus.hw07;

/** Слушатель комманд департамента. */
public interface ATMDepartmentCommandListener {
    void onReset();
    int onGetBalance();
}
