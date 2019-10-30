package ru.otus.hw05.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/** "Запускалка теста", которая принимает класс с тестами, анализирует аннотации и выполняет методы. */
public class TestRunner {

    public static void run(Class<?> clazz) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("Start tests for class: " + clazz.getName());
        // переменные для сбора статистики
        int passedTests = 0;
        int failedTests = 0;
        // анализ методов
        List<Method> methodsBefore = getAnnotatedMethods(clazz, Before.class);
        List<Method> methodsAfter = getAnnotatedMethods(clazz, After.class);
        List<Method> methodsTest = getAnnotatedMethods(clazz, Test.class);
        // прогоняем тесты
        for(Method test: methodsTest) {
            Object instance = clazz.getConstructor().newInstance();
            String errorMessage = null;
            try {
                invokeMethods(instance, methodsBefore);
                test.invoke(instance);
                invokeMethods(instance, methodsAfter);
                passedTests++;
            } catch (Exception e) {
                errorMessage = e.getCause().getMessage();
                failedTests++;
            }
            System.out.println(String.format("Test: %s - %s",
                    getTestName(test), errorMessage==null ? "PASSED" : "FAILED"));
            if (errorMessage!=null) {
                System.out.println(errorMessage);
            }
        }
        // печатаем статистику
        System.out.println(String.format("All tests: %d, passed: %d, failed: %d",
                passedTests+failedTests,passedTests,failedTests));
    }

    /** Получение названия теста. */
    private static String getTestName(Method test) {
        String testName = test.getAnnotation(Test.class).name();
        return testName.isEmpty() ? test.getName() : testName;
    }

    /** Вызов списка методов. */
    private static void invokeMethods(Object instance, List<Method> methods) {
        methods.forEach(method -> {
            try {
                method.invoke(instance);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new Error(e);
            }
        });
    }

    /** Получение всех методов, отмеченных указанной аннотацией. */
    private static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getDeclaredAnnotation(annotation)!=null)
                .collect(Collectors.toList());
    }
}
