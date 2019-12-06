package ru.otus.hw09.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw09.orm.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DbExecutor<T> {
    private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);

    private Connection connection;
    private final Class<T> clazz;
    private final String selectStmt;
    private final String insertStmt;

    public DbExecutor(Connection connection, Class<T> clazz) {
        this.connection = connection;
        this.clazz = clazz;
        this.selectStmt = createSelectStatementForClass(this.clazz);
        this.insertStmt = createInsertStatementForClass(this.clazz);
    }

    public void create(T objectData) throws SQLException, IllegalAccessException {
        Savepoint savePoint = connection.setSavepoint("savePointCreate");
        Class<T> clazz = (Class<T>) objectData.getClass();
        try (PreparedStatement pst = connection.prepareStatement(this.insertStmt,
                Statement.NO_GENERATED_KEYS)) {
            List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .collect(Collectors.toList());
            for (int idx = 0; idx < fields.size(); idx++) {
                Field field = fields.get(idx);
                if (!Modifier.isPublic(field.getModifiers())) field.setAccessible(true);
                pst.setObject(idx + 1, field.get(objectData));
            }
            pst.executeUpdate();
        } catch (SQLException | IllegalAccessException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    public void update(T objectData) {

    }

    public void createOrUpdate(T objectData) {
        throw new UnsupportedOperationException();
    }

    public Optional<T> load(long id, Class<T> clazz) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement(this.selectStmt)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                try {
                    if (rs.next()) {
                        T obj = clazz.getConstructor().newInstance();
                        // заполняем не финальные, и не статические поля
                        for (Field field : clazz.getDeclaredFields()) {
                            int modifiers = field.getModifiers();
                            if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) continue;
                            if (!Modifier.isPublic(modifiers)) field.setAccessible(true);
                            field.set(obj, rs.getObject(field.getName()));
                        }
                        return Optional.of(obj);
                    }
                } catch (SQLException | NoSuchMethodException
                        | InvocationTargetException | InstantiationException
                        | IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                }
                return Optional.empty();
            }
        }
    }

    private String createInsertStatementForClass(Class<T> clazz) {
        List<String> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .map(Field::getName)
                .collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into ");
        stringBuilder.append(clazz.getSimpleName().toLowerCase());
        stringBuilder.append("(");
        stringBuilder.append(String.join(",", fields));
        stringBuilder.append(")");
        stringBuilder.append(" values(");
        stringBuilder.append(String.join(",", Collections.nCopies(fields.size(), "?")));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private String createSelectStatementForClass(Class<T> clazz) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select ");
        stringBuilder.append(Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .map(Field::getName)
                .collect(Collectors.joining(", ")));
        stringBuilder.append(" from ");
        stringBuilder.append(clazz.getSimpleName().toLowerCase());
        stringBuilder.append(" where ");
        stringBuilder.append(getIdField(clazz));
        stringBuilder.append(" = ?");
        return stringBuilder.toString();
    }

    private String getIdField(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .map(Field::getName)
                .findFirst().orElse("id");
    }
}
