package ru.otus.hw05.framework;

/** Минимальная реализация для удобства написания теста. */
public class AssertEquals {
    public static void assertEquals(int expected, int actual) {
        if (expected != actual)
            throw new AssertionError(String.format("Expected %d, but has %d!", expected, actual));
    }
}
