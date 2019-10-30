package ru.otus.hw05;

import ru.otus.hw05.framework.TestRunner;

import java.lang.reflect.InvocationTargetException;

/** Класс для демонстраии запуска тестов. */
public class Demo {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        // пример запуска теста
        TestRunner.run(SimpleTest.class);
    }
}
