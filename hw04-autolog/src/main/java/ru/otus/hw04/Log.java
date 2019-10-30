package ru.otus.hw04;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Аннотация для включения логирования параметров, с которыми вызывается аннотированный метод.
 *  RetentionPolicy.RUNTIME - выставлен для возможности обработки аннотации во время выполнения программы.
 *  */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
}
