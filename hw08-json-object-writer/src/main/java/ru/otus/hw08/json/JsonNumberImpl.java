package ru.otus.hw08.json;

import javax.json.JsonNumber;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumberImpl implements JsonNumber {

    private Number value;

    public JsonNumberImpl(Number value) {
        this.value = value;
    }

    @Override
    public boolean isIntegral() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public int intValueExact() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public long longValueExact() {
        return value.longValue();
    }

    @Override
    public BigInteger bigIntegerValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BigInteger bigIntegerValueExact() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public BigDecimal bigDecimalValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ValueType getValueType() {
        return ValueType.NUMBER;
    }

    @Override
    public String toString() {
        String str = String.valueOf(value);
        return str;
    }
}
