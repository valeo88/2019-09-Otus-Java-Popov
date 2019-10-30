package ru.otus.hw04;

import java.lang.reflect.InvocationTargetException;

/** Класс для проверки работы метода, аннотированного {@link Log}. */
public class Demo {
    public static void main(String[] args) throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {
        ITestLogging testLogging = IoC.createInstance(TestLogging.class);

        System.out.println("Invoke method with @Log annotation.");
        testLogging.sum(1,2);

        System.out.println("Invoke method without @Log annotation.");
        testLogging.diff(3,1);
    }
}
