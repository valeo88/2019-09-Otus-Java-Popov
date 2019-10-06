package ru.otus.hw02;

import java.util.Collections;
import java.util.List;

public class DIYArrayListDemo {
    public static void main(String[] args) {
        //simpleDemo();
        collectionsMethodsDemo();
    }

    /** Some simple methods test on DIYArrayList */
    private static void simpleDemo() {
        System.out.println("========== SIMPLE DEMO ============");
        List<Integer> list1 = new DIYArrayList<>();
        for (int i = 0; i < 25; i++) {
            list1.add(i);
        }
        System.out.println("Creating list with 25 elements: " + list1);
        System.out.println("Size: " + list1.size());

        list1.add(100);
        System.out.println("Method add(100): " + list1);

        System.out.println("Method contains(17): " + list1.contains(17));
        System.out.println("Method contains(-1): " + list1.contains(-1));

        System.out.println("======================");
    }

    /** Some methods of java.util.Collections test on DIYArrayList */
    private static void collectionsMethodsDemo() {
        System.out.println("========== java.util.Collections DEMO ============");
        List<String> list = new DIYArrayList<>();
        list.add("Otus");
        list.add("Java");
        list.add("2019");

        Collections.addAll(list, "Lorem",
                "Ipsum", "is", "simply", "dummy", "text", "of", "the", "printing", "and", "typesetting", "industry",
                "Lorem", "Ipsum", "has", "been", "the", "industry", "...");
        System.out.println("List afted addAll method: " + list);
        System.out.println("Size (22 expected): " + list.size());

        // create enough size list with empty string
        List<String> listCopy = new DIYArrayList<>(Collections.nCopies(list.size(), ""));
        Collections.copy(listCopy, list);
        System.out.println("Copy of first list: " + listCopy);



        System.out.println("======================");
    }
}
