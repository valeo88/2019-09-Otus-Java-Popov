package ru.otus.hw02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for DIYArrayList")
public class DIYArrayListTest {

    @Test
    public void testDefaultConstructor() {
        List<Integer> list = new DIYArrayList<>();

        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    public void testCollectionsConstructor() {
        List<Integer> initial = Arrays.asList(1,2,3,4,5,6);

        List<Integer> list = new DIYArrayList<>(initial);

        assertEquals(initial.size(), list.size());
        assertArrayEquals(initial.toArray(), list.toArray());
    }

    @Test
    public void testAddContains() {
        List<Integer> list = new DIYArrayList<>();

        list.add(100);
        list.add(12);

        assertTrue(list.contains(100));
        assertFalse(list.contains(-1));
    }

    @Test
    public void testAddAll() {
        List<Integer> list = new DIYArrayList<>();
        List<Integer> data = Arrays.asList(10,12,8,18,2,8,89,12,34);

        list.addAll(data);

        assertTrue(list.contains(data.get(3)));
        assertEquals(data.size(), list.size());
    }

    @Test
    public void testClear() {
        List<Integer> list = new DIYArrayList<>(Arrays.asList(1,2,3,4,5,6));

        list.clear();

        assertEquals(0, list.size());
    }

    @Test
    public void testCollectionsAddAll() {
        List<String> list = new DIYArrayList<>();
        list.add("Otus");
        list.add("Java");
        list.add("2019");

        Collections.addAll(list, "Lorem",
                "Ipsum", "is", "simply", "dummy", "text", "of", "the", "printing", "and", "typesetting", "industry",
                "Lorem", "Ipsum", "has", "been", "the", "industry", "...");

        assertEquals(22, list.size());
        assertEquals("2019", list.get(2));
        assertEquals("...", list.get(list.size()-1));
    }

    @Test
    @DisplayName("Collections.copy from DIYArrayList to DIYArrayList")
    public void testCollectionsCopy1() {
        List<Integer> initialList = new DIYArrayList<>(Arrays.asList(100,12,-1,3121,883,1828,66,8282,7171,7272,2828,
                828,62,12,64,181,82,282,494,22,93,383,3,3,4));
        // чтобы копирование корректно работало, необходимо предварительно заполнять массив для копирования
        List<Integer> listCopy = new DIYArrayList<>(Collections.nCopies(initialList.size(), 0));

        Collections.copy(listCopy, initialList);

        assertArrayEquals(listCopy.toArray(), initialList.toArray());
    }

    @Test
    @DisplayName("Collections.copy from ArrayList to DIYArrayList")
    public void testCollectionsCopy2() {
        List<Integer> initialList2 = new ArrayList<>(Arrays.asList(100,12,-1,3121,883,1828,66,8282,7171,7272,2828,
                828,62,12,64,181,82,282,494,22,93,383,3,3,4));
        // чтобы копирование корректно работало, необходимо предварительно заполнять массив для копирования
        List<Integer> listCopy2 = new DIYArrayList<>(Collections.nCopies(initialList2.size(), 0));

        Collections.copy(listCopy2, initialList2);

        assertArrayEquals(listCopy2.toArray(), initialList2.toArray());
    }

    @Test
    public void testCollectionsSort() {
        List<Integer> list = new DIYArrayList<>(Arrays.asList(100,12,-1,3121,883,1828,66,8282,7171,7272,2828,
                828,62,12,64,181,82,282,494,22,93,383,3,3,4));
        Integer[] arrSorted = new Integer[]{-1,3,3,4,12,12,22,62,64,66,82,93,100,181,282,383,494,828,883,1828,2828,3121,
                7171,7272,8282};

        Collections.sort(list, (Integer::compareTo));

        assertArrayEquals(arrSorted, list.toArray());
    }
}
