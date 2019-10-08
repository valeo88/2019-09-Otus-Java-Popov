package ru.otus.hw02;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DIYArrayListTest {


    @Test
    public void testDefaultConstructor() {
        List<Integer> list = new DIYArrayList<>();
        Assert.assertEquals(0, list.size());
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void testCollectionsConstructor() {
        List<Integer> initial = Arrays.asList(1,2,3,4,5,6);
        List<Integer> list = new DIYArrayList<>(initial);
        Assert.assertEquals(initial.size(), list.size());
        Assert.assertArrayEquals(initial.toArray(), list.toArray());
    }

    @Test
    public void testAddContains() {
        List<Integer> list = new DIYArrayList<>();
        list.add(100);
        list.add(12);
        Assert.assertTrue(list.contains(100));
        Assert.assertFalse(list.contains(-1));
    }

    @Test
    public void testAddAll() {
        List<Integer> list = new DIYArrayList<>();
        List<Integer> data = Arrays.asList(10,12,8,18,2,8,89,12,34);
        list.addAll(data);
        Assert.assertTrue(list.contains(data.get(3)));
        Assert.assertEquals(data.size(), list.size());
    }

    @Test
    public void testClear() {
        List<Integer> list = new DIYArrayList<>(Arrays.asList(1,2,3,4,5,6));
        list.clear();
        Assert.assertEquals(0, list.size());
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
        Assert.assertEquals(22, list.size());
        Assert.assertEquals("2019", list.get(2));
        Assert.assertEquals("...", list.get(list.size()-1));
    }

    @Test
    public void testCollectionsCopy() {
        // копирование из DIYArrayList в DIYArrayList
        List<Integer> initialList = new DIYArrayList<>(Arrays.asList(100,12,-1,3121,883,1828,66,8282,7171,7272,2828,
                828,62,12,64,181,82,282,494,22,93,383,3,3,4));
        // чтобы копирование корректно работало, необходимо предварительно заполнять массив для копирования
        List<Integer> listCopy = new DIYArrayList<>(Collections.nCopies(initialList.size(), 0));
        Collections.copy(listCopy, initialList);
        Assert.assertArrayEquals(listCopy.toArray(), initialList.toArray());

        // копирование из ArrayList в DIYArrayList
        List<Integer> initialList2 = new ArrayList<>(Arrays.asList(100,12,-1,3121,883,1828,66,8282,7171,7272,2828,
                828,62,12,64,181,82,282,494,22,93,383,3,3,4));
        // чтобы копирование корректно работало, необходимо предварительно заполнять массив для копирования
        List<Integer> listCopy2 = new DIYArrayList<>(Collections.nCopies(initialList2.size(), 0));
        Collections.copy(listCopy2, initialList2);
        Assert.assertArrayEquals(listCopy2.toArray(), initialList2.toArray());
    }

    @Test
    public void testCollectionsSort() {
        List<Integer> list = new DIYArrayList<>(Arrays.asList(100,12,-1,3121,883,1828,66,8282,7171,7272,2828,
                828,62,12,64,181,82,282,494,22,93,383,3,3,4));
        Integer[] arrSorted = new Integer[]{-1,3,3,4,12,12,22,62,64,66,82,93,100,181,282,383,494,828,883,1828,2828,3121,
                7171,7272,8282};
        Collections.sort(list, (Integer::compareTo));
        Assert.assertArrayEquals(arrSorted, list.toArray());
    }
}
