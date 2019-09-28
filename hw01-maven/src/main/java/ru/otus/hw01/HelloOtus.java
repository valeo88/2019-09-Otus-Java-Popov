package ru.otus.hw01;

import com.google.common.collect.HashMultiset;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/** Class for test some methods from Google Guava library. */
public class HelloOtus {

    public static void main(String... args) {
        printHashExample();
        printHashMultisetExample();
    }

    /** Print guava hash multiset example. */
    private static void printHashMultisetExample() {
        HashMultiset<String> set = HashMultiset.create();
        set.add("Hello");
        set.add("Otus");
        set.add("Otus");
        System.out.println("Hash multiset: " + set.toString());
    }

    /** Print SHA512 hash of example string. */
    private static void printHashExample() {
        String example = "Hello Otus";
        String sha512Hash = Hashing.sha512()
                .hashString(example, Charset.defaultCharset()).toString();
        System.out.println(String.format("SHA512 hash of %s : %s", example, sha512Hash));
    }
}