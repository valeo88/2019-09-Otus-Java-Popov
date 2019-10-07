package ru.otus.hw02;

import java.util.*;

public class DIYArrayList<T> implements List<T> {

    // вместимость по умолчанию для массива значений
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    // коэффициент роста вместимости массива (2 - чтобы была усредненная постоянная сложность вставки в конец)
    private static final int GROW_RATE = 2;

    // массив для хранения элементов
    private Object[] arr;
    // индекс последнего элемента в массиве
    private int lastElementIndex = -1;

    public DIYArrayList() {
        arr = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public DIYArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            arr = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            arr = new Object[0];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    public DIYArrayList(Collection<? extends T> c) {
        arr = new Object[c.size()];
        addAll(c);
    }

    @Override
    public int size() {
        return Math.max(lastElementIndex + 1, 0);
    }

    @Override
    public boolean isEmpty() {
        return lastElementIndex == -1;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i <= lastElementIndex; i++) {
            if (Objects.equals(o, arr[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size()];
        for (int i = 0; i <= lastElementIndex; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < lastElementIndex) {
            a = (T1[]) toArray();
        } else {
            for (int i = 0; i <= lastElementIndex; i++) {
                a[i] = (T1) arr[i];
            }
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        if (lastElementIndex >= arr.length - 1) {
            // не хватает места в массиве - создаем новый в GROW_RATE больше и копируем туда элементы
            arr = toArray(new Object[arr.length * GROW_RATE]);
        }
        lastElementIndex++;
        arr[lastElementIndex] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for(T t: c) {
            add(t);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        Object[] newArr;
        int newLastUsedIndex = -1;
        if (lastElementIndex + c.size() >= arr.length - 1) {
            newArr = new Object[arr.length * GROW_RATE];
        } else {
            newArr = new Object[arr.length];
        }
        // добавляем [0,index-1] элементы
        for (int i = 0; i < index; i++) {
            newArr[++newLastUsedIndex] = arr[i];
        }
        // добавляем элементы из коллекции c
        for (T t : c) {
            newArr[++newLastUsedIndex] = t;
        }
        // добавляем [index,lastUsedIndex] элементы
        for (int i = index; i <= lastElementIndex; i++) {
            newArr[++newLastUsedIndex] = arr[i];
        }
        arr = newArr;
        lastElementIndex = newLastUsedIndex;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        arr = new Object[0];
        lastElementIndex = -1;
    }

    @Override
    public T get(int index) {
        return (T) arr[index];
    }

    @Override
    public T set(int index, T element) {
        arr[index] = element;
        return (T) arr[index];
    }

    @Override
    public void add(int index, T element) {
        addAll(index, Arrays.asList(element));
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i <= lastElementIndex; i++) {
            if (Objects.equals(arr[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = lastElementIndex; i >= 0; i--) {
            if (Objects.equals(arr[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DIYListItr();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    /** Реализация итератора ListIterator для метода {@link java.util.Collections#copy(List, List)}  */
    private class DIYListItr implements ListIterator<T> {

        private int cursor = -1;

        DIYListItr() {
        }

        DIYListItr(int index) {
            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor < lastElementIndex;
        }

        @Override
        public T next() {
            if (cursor >= lastElementIndex)
                throw new NoSuchElementException();
            cursor++;
            return (T) arr[cursor];
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            if (cursor <= 0)
                throw new NoSuchElementException();
            cursor--;
            return (T) arr[cursor];
        }

        @Override
        public int nextIndex() {
            return cursor >= lastElementIndex ? size() : cursor + 1;
        }

        @Override
        public int previousIndex() {
            return cursor == 0 ? -1 : cursor - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T t) {
            arr[cursor] = t;
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }
    }
}
