package ru.otus.hw08.json;

import javax.json.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonArrayImpl extends AbstractList<JsonValue> implements JsonArray {

    private List<JsonValue> values = new ArrayList<>();

    @Override
    public JsonObject getJsonObject(int i) {
        return (JsonObject) get(i);
    }

    @Override
    public JsonArray getJsonArray(int i) {
        return (JsonArray) get(i);
    }

    @Override
    public JsonNumber getJsonNumber(int i) {
        return (JsonNumber) get(i);
    }

    @Override
    public JsonString getJsonString(int i) {
        return (JsonString) get(i);
    }

    @Override
    public <T extends JsonValue> List<T> getValuesAs(Class<T> aClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getString(int i) {
        return getJsonString(i).getString();
    }

    @Override
    public String getString(int i, String s) {
        try {
            return getString(i);
        } catch (Exception e) {
            return s;
        }
    }

    @Override
    public int getInt(int i) {
        return getJsonNumber(i).intValue();
    }

    @Override
    public int getInt(int i, int i1) {
        try {
            return getInt(i);
        } catch (Exception e) {
            return i1;
        }
    }

    @Override
    public boolean getBoolean(int i) {
        JsonValue value = get(i);
        if (value == null) {
            throw new NullPointerException();
        } else if (value == JsonValue.TRUE) {
            return true;
        } else if (value == JsonValue.FALSE) {
            return false;
        } else {
            throw new ClassCastException();
        }
    }

    @Override
    public boolean getBoolean(int i, boolean b) {
        try {
            return getBoolean(i);
        } catch (Exception e) {
            return b;
        }
    }

    @Override
    public boolean isNull(int i) {
        return get(i) == JsonValue.NULL;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.ARRAY;
    }

    @Override
    public JsonValue get(int index) {
        return values.get(index);
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean add(JsonValue jsonValue) {
        return values.add(jsonValue);
    }

    @Override
    public String toString() {
        String jsonString = "[" +
                values.stream().map(JsonValue::toString).collect(Collectors.joining(",")) +
                "]";
        return jsonString;
    }
}
