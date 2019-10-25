package ru.otus.hw04;

/** Интерфейс для возможности создания прокси-объектов для класса {@link TestLogging}. */
public interface ITestLogging {

    @Log
    int sum(int a, int b);

    int diff(int a, int b);
}
