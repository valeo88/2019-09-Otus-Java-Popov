package ru.otus.hw08.json;

import javax.json.*;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JsonObjectImpl extends AbstractMap<String, JsonValue> implements JsonObject {

    private Map<String, JsonValue> attributes;

    public JsonObjectImpl(Map<String, JsonValue> attributes) {
        this.attributes = attributes;
    }

    @Override
    public JsonArray getJsonArray(String s) {
        return (JsonArray) attributes.get(s);
    }

    @Override
    public JsonObject getJsonObject(String s) {
        return (JsonObject) attributes.get(s);
    }

    @Override
    public JsonNumber getJsonNumber(String s) {
        return (JsonNumber) attributes.get(s);
    }

    @Override
    public JsonString getJsonString(String s) {
        return (JsonString) attributes.get(s);
    }

    @Override
    public String getString(String s) {
        return attributes.get(s).toString();
    }

    @Override
    public String getString(String s, String s1) {
        return attributes.containsKey(s) ? attributes.get(s).toString() : s1;
    }

    @Override
    public int getInt(String s) {
        return getJsonNumber(s).intValue();
    }

    @Override
    public int getInt(String s, int i) {
        try {
            return getInt(s);
        } catch (Exception ignored) {
            return i;
        }
    }

    @Override
    public boolean getBoolean(String s) {
        JsonValue value = this.get(s);
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
    public boolean getBoolean(String s, boolean b) {
        try {
            return getBoolean(s);
        } catch (Exception ignored) {
            return b;
        }
    }

    @Override
    public JsonValue put(String key, JsonValue value) {
        return attributes.put(key, value);
    }

    @Override
    public boolean isNull(String s) {
        return attributes.get(s) == null;
    }

    @Override
    public int size() {
        return attributes.size();
    }

    @Override
    public Set<Entry<String, JsonValue>> entrySet() {
        return attributes.entrySet();
    }

    @Override
    public ValueType getValueType() {
        return ValueType.OBJECT;
    }

    @Override
    public String toString() {
        return "{" + attributes.entrySet().stream().map(stringJsonValueEntry -> {
            return String.format("\"%s\":%s", stringJsonValueEntry.getKey(), stringJsonValueEntry.getValue());
        }).collect(Collectors.joining(",")) + "}";
    }
}
