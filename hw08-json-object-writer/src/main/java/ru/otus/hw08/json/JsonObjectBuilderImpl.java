package ru.otus.hw08.json;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;

public class JsonObjectBuilderImpl implements JsonObjectBuilder {

    private JsonObject jsonObject = new JsonObjectImpl(new LinkedHashMap<>());

    @Override
    public JsonObjectBuilder add(String s, JsonValue jsonValue) {
        jsonObject.put(s, jsonValue);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String s, String s1) {
        jsonObject.put(s, new JsonStringImpl(s1));
        return this;
    }

    @Override
    public JsonObjectBuilder add(String s, BigInteger bigInteger) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObjectBuilder add(String s, BigDecimal bigDecimal) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonObjectBuilder add(String s, int i) {
        jsonObject.put(s, new JsonNumberImpl(i));
        return this;
    }

    @Override
    public JsonObjectBuilder add(String s, long l) {
        jsonObject.put(s, new JsonNumberImpl(l));
        return this;
    }

    @Override
    public JsonObjectBuilder add(String s, double v) {
        jsonObject.put(s, new JsonNumberImpl(v));
        return this;
    }

    @Override
    public JsonObjectBuilder add(String s, boolean b) {
        jsonObject.put(s, b ? JsonValue.TRUE : JsonValue.FALSE);
        return this;
    }

    @Override
    public JsonObjectBuilder addNull(String s) {
        jsonObject.put(s, JsonValue.NULL);
        return this;
    }

    @Override
    public JsonObjectBuilder add(String s, JsonObjectBuilder jsonObjectBuilder) {
        jsonObject.put(s, jsonObjectBuilder.build());
        return this;
    }

    @Override
    public JsonObjectBuilder add(String s, JsonArrayBuilder jsonArrayBuilder) {
        jsonObject.put(s, jsonArrayBuilder.build());
        return this;
    }

    @Override
    public JsonObject build() {
        return jsonObject;
    }
}
