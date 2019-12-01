package ru.otus.hw08.json;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonArrayBuilderImpl implements JsonArrayBuilder {

    private JsonArray jsonArray = new JsonArrayImpl();

    @Override
    public JsonArrayBuilder add(JsonValue jsonValue) {
        jsonArray.add(jsonValue);
        return this;
    }

    @Override
    public JsonArrayBuilder add(String s) {
        jsonArray.add(new JsonStringImpl(s));
        return this;
    }

    @Override
    public JsonArrayBuilder add(BigDecimal bigDecimal) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArrayBuilder add(BigInteger bigInteger) {
        throw new UnsupportedOperationException();
    }

    @Override
    public JsonArrayBuilder add(int i) {
        jsonArray.add(new JsonNumberImpl(i));
        return this;
    }

    @Override
    public JsonArrayBuilder add(long l) {
        jsonArray.add(new JsonNumberImpl(l));
        return this;
    }

    @Override
    public JsonArrayBuilder add(double v) {
        jsonArray.add(new JsonNumberImpl(v));
        return this;
    }

    @Override
    public JsonArrayBuilder add(boolean b) {
        jsonArray.add(b ? JsonValue.TRUE : JsonValue.FALSE);
        return this;
    }

    @Override
    public JsonArrayBuilder addNull() {
        jsonArray.add(JsonValue.NULL);
        return this;
    }

    @Override
    public JsonArrayBuilder add(JsonObjectBuilder jsonObjectBuilder) {
        jsonArray.add(jsonObjectBuilder.build());
        return this;
    }

    @Override
    public JsonArrayBuilder add(JsonArrayBuilder jsonArrayBuilder) {
        jsonArray.addAll(jsonArrayBuilder.build());
        return this;
    }



    @Override
    public JsonArray build() {
        return jsonArray;
    }
}
