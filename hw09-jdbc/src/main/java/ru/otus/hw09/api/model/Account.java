package ru.otus.hw09.api.model;

import ru.otus.hw09.orm.Id;

import java.math.BigDecimal;

/** Счет. */
public class Account {
    @Id
    private long no;
    private String type;
    private BigDecimal rest;

    public long getNo() {
        return no;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getRest() {
        return rest;
    }

    public void setRest(BigDecimal rest) {
        this.rest = rest;
    }
}
