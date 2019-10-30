package ru.otus.hw05;

import ru.otus.hw05.framework.After;
import ru.otus.hw05.framework.Before;
import ru.otus.hw05.framework.Test;

import static ru.otus.hw05.framework.AssertEquals.assertEquals;

/** Класс с тестами для пример. */
public class SimpleTest {
    private int a;
    private int b;

    @Before
    public void before() {
        a = 100;
        b = 10;
    }

    @After
    public void after() {
        a = 0;
        b = 0;
    }

    @Test(name = "Testing sum")
    public void testSum() {
        int c = a + b;

        assertEquals(110, c);
    }

    @Test
    public void testException() {
        throw new RuntimeException("Something wrong...");
    }

    @Test(name = "Testing wrong division")
    public void testWrongDiv() {
        int c = a / b;

        assertEquals(11, c);
    }
}
