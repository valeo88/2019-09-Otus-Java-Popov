package ru.otus.hw08.json;

import javax.json.JsonString;

public class JsonStringImpl implements JsonString {

    private String value;

    public JsonStringImpl(String value) {
        this.value = value;
    }

    @Override
    public String getString() {
        return value;
    }

    @Override
    public CharSequence getChars() {
        return value;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.STRING;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
