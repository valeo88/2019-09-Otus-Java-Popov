package ru.otus.hw05.framework;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
/** Метод, помеченный этой аннотацией должен выполняется перед каждым тестом. */
public @interface Before {
}
