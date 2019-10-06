package ru.otus.hw02;

import java.util.*;

public class DIYArrayList<T> implements List<T> {

    // default capacity of initial array
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    // array grow rate - because of 2 we can guarantee like constant time adding to end
    private static final int GROW_RATE = 2;

    // array for storing elements
    private Object[] arr;
    // index of last element in array
    private int lastUsedIndex = -1;

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
        return Math.max(lastUsedIndex+1, 0);
    }

    @Override
    public boolean isEmpty() {
        return lastUsedIndex == -1;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i <= lastUsedIndex; i++) {
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
        for (int i = 0; i <= lastUsedIndex; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < lastUsedIndex) {
            a = (T1[]) toArray();
        } else {
            for (int i = 0; i <= lastUsedIndex; i++) {
                a[i] = (T1) arr[i];
            }
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        if (lastUsedIndex >= arr.length - 1) {
            // grow array 2 times and copy elements
            arr = toArray(new Object[arr.length * GROW_RATE]);
        }
        lastUsedIndex++;
        arr[lastUsedIndex] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
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
        if (lastUsedIndex + c.size() >= arr.length - 1) {
            newArr = new Object[arr.length * GROW_RATE];
        } else {
            newArr = new Object[arr.length];
        }
        // copy first [0,index-1] elements
        for (int i = 0; i < index; i++) {
            newArr[++newLastUsedIndex] = arr[i];
        }
        // add elements from c
        for (T t : c) {
            newArr[++newLastUsedIndex] = t;
        }
        // add elements [index,lastUsedIndex]
        for (int i = index; i <= lastUsedIndex; i++) {
            newArr[++newLastUsedIndex] = arr[i];
        }
        arr = newArr;
        lastUsedIndex = newLastUsedIndex;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        arr = new Object[0];
        lastUsedIndex = -1;
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
        return null;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i <= lastUsedIndex; i++) {
            if (Objects.equals(arr[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = lastUsedIndex; i >= 0; i--) {
            if (Objects.equals(arr[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DIYListItr(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    /** toString implemented for pretty print. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DIYArrayList{");
        for (int i = 0; i <= lastUsedIndex; i++) {
            sb.append(arr[i]);
            sb.append(",");
        }
        sb.append("}");
        return sb.toString();
    }

    /** ListIterator implementation */
    private class DIYListItr implements ListIterator<T> {

        private int cursor = 0;

        DIYListItr(int index) {
            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor < size() - 1;
        }

        @Override
        public T next() {
            int i = cursor;
            if (i >= size())
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
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            cursor = i;
            return (T) arr[i];
        }

        @Override
        public int nextIndex() {
            return cursor >= size() - 1 ? size() : cursor + 1;
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
