package ru.otus.hw08.examples;

public class Primitives {

    private static final String aaaa = "aaaaaaaaaaaaaaaaaa";

    private byte b = 17;
    private short s = 8373;
    private int x = 1;
    private long y = 1921929L;

    private char c = 'a';
    private float f = 121.1f;
    private double d = 10212.1;
    private boolean isOk = true;

    private Integer nI = null;

    private transient int ti = x + 12;

    public byte getB() {
        return b;
    }

    public short getS() {
        return s;
    }

    public int getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public char getC() {
        return c;
    }

    public float getF() {
        return f;
    }

    public double getD() {
        return d;
    }

    public boolean isOk() {
        return isOk;
    }
}
