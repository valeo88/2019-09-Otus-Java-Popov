package ru.otus.hw08;

import ru.otus.hw08.json.JsonArrayBuilderImpl;
import ru.otus.hw08.json.JsonNumberImpl;
import ru.otus.hw08.json.JsonObjectBuilderImpl;
import ru.otus.hw08.json.JsonStringImpl;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

/** Кастомный object to json string writer. */
public class CustomGson {
    // по умолчанию в GSON null-поля не сериализуются
    private boolean serializeNulls = false;

    public CustomGson() {
    }

    public CustomGson(boolean serializeNulls) {
        this.serializeNulls = serializeNulls;
    }

    /** Преобразование объекта в json-строку. */
    public String toJson(Object object) {
        JsonValue jsonObject;
        try {
            jsonObject = create(object, serializeNulls);
        } catch (IllegalAccessException e) {
            throw new CustomGsonException(e.getMessage());
        }
        return jsonObject.toString();
    }

    /** Рекурсивное создание Json объекта. */
    private static JsonValue create(Object object, boolean serializeNulls) throws IllegalAccessException {
        JsonObjectBuilder builder = new JsonObjectBuilderImpl();
        if (object == null) {
            return JsonValue.NULL;
        } else if (TypeChecker.isNumber(object.getClass())) {
            return new JsonNumberImpl((Number) object);
        } else if (TypeChecker.isBoolean(object.getClass())) {
            return (boolean) object ? JsonValue.TRUE : JsonValue.FALSE;
        } else if (TypeChecker.isChar(object.getClass())) {
            return new JsonStringImpl(String.valueOf(object));
        } else if (String.class.equals(object.getClass())) {
            return new JsonStringImpl(String.valueOf(object));
        } else if (TypeChecker.isArray(object.getClass())) {
            JsonArrayBuilder jsonArrayBuilder = new JsonArrayBuilderImpl();
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++) {
                jsonArrayBuilder.add(create(Array.get(object, i), serializeNulls));
            }
            return jsonArrayBuilder.build();
        } else if (object instanceof Collection) {
            JsonArrayBuilder jsonArrayBuilder = new JsonArrayBuilderImpl();
            for (Object o : (Collection<?>) object) {
                jsonArrayBuilder.add(create(o, serializeNulls));
            }
            return jsonArrayBuilder.build();
        } else {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                final int modifiers = field.getModifiers();

                // статические и транзиентные поля не нужно преобразовывать
                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;

                if (!Modifier.isPublic(modifiers)) field.setAccessible(true);
                Object fieldValue = field.get(object);
                if (fieldValue==null && !serializeNulls) continue;
                builder.add(fieldName, create(fieldValue, serializeNulls));
            }
        }
        return builder.build();
    }

}
