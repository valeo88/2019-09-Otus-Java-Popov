package ru.otus.hw08;

/** Вспомогательные методы для типов полей классов. */
public class TypeChecker {

    public static boolean isNumber(Class<?> clazz) {
        return isByte(clazz) || isShort(clazz) || isInt(clazz) || isLong(clazz) || isFloat(clazz) || isDouble(clazz);
    }

    public static boolean isChar(Class<?> clazz) {
        return clazz.equals(Character.class) || clazz.equals(char.class);
    }

    public static boolean isBoolean(Class<?> clazz) {
        return clazz.equals(Boolean.class) || clazz.equals(boolean.class);
    }

    public static boolean isArray(Class<?> clazz) {
        return clazz.isArray();
    }

    private static boolean isByte(Class<?> clazz) {
        return clazz.equals(Byte.class) || clazz.equals(byte.class);
    }

    private static boolean isShort(Class<?> clazz) {
        return clazz.equals(Short.class) || clazz.equals(short.class);
    }

    private static boolean isInt(Class<?> clazz) {
        return clazz.equals(Integer.class) || clazz.equals(int.class);
    }

    private static boolean isLong(Class<?> clazz) {
        return clazz.equals(Long.class) || clazz.equals(long.class);
    }

    private static boolean isFloat(Class<?> clazz) {
        return clazz.equals(Float.class) || clazz.equals(float.class);
    }

    private static boolean isDouble(Class<?> clazz) {
        return clazz.equals(Double.class) || clazz.equals(double.class);
    }
}
