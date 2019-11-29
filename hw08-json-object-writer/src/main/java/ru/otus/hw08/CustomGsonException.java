package ru.otus.hw08;

/** Исключения, возникающие при преобразовании в Json. */
public class CustomGsonException extends RuntimeException {
    public CustomGsonException() {
        super();
    }

    public CustomGsonException(String message) {
        super(message);
    }
}
