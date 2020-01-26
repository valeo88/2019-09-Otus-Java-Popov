package ru.otus.hw14;

public interface SequenceGenerator<T> {

    boolean hasNext(String seqName);

    T next(String seqName);
}
