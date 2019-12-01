package ru.otus.hw08;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw08.examples.CollectionsExample;
import ru.otus.hw08.examples.Primitives;
import ru.otus.hw08.examples.PrimitivesWithArrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomGsonTest {

    private CustomGson customGsonDefault;
    private Gson gsonDefault;
    private CustomGson customGsonSN;
    private Gson gsonSN;

    @BeforeEach
    void before() {
        customGsonDefault = new CustomGson();
        gsonDefault = new Gson();

        customGsonSN = new CustomGson(true);
        gsonSN = new GsonBuilder().serializeNulls().create();
    }

    @Test
    void toJsonNull() {
        assertEquals(gsonDefault.toJson(null), customGsonDefault.toJson(null));
        assertEquals(gsonSN.toJson(null), customGsonSN.toJson(null));
    }

    @Test
    void toJsonPrimitives() {
        final Primitives obj = new Primitives();

        assertEquals(gsonDefault.toJson(obj), customGsonDefault.toJson(obj));
        assertEquals(gsonSN.toJson(obj), customGsonSN.toJson(obj));
    }

    @Test
    void toJsonPrimitivesWithArray() {
        final PrimitivesWithArrays obj = new PrimitivesWithArrays();

        assertEquals(gsonDefault.toJson(obj), customGsonDefault.toJson(obj));
        assertEquals(gsonSN.toJson(obj), customGsonSN.toJson(obj));
    }

    @Test
    void toJsonCollections() {
        final CollectionsExample obj = new CollectionsExample();

        assertEquals(gsonDefault.toJson(obj), customGsonDefault.toJson(obj));
        assertEquals(gsonSN.toJson(obj), customGsonSN.toJson(obj));
    }
}