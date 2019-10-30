package ru.otus.hw05.framework;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
/** Метод, помеченный этой аннотацией считается тестом. */
public @interface Test {
    String name() default "";
}
