package ru.otus.hw04;

/** Класс для тестирования аннотации {@link Log}. */
public class TestLogging implements ITestLogging {

    @Log
    @Override
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    public int diff(int a, int b) {
        return a - b;
    }
}
