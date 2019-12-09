package ru.otus.hw09.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DbExecutor<T> {
    private static Logger logger = LoggerFactory.getLogger(DbExecutor.class);

    private Connection connection;
    private final Class<T> clazz;
    private final String selectStmt;
    private final String insertStmt;
    private final String updateStmt;

    public DbExecutor(Connection connection, Class<T> clazz) {
        this.connection = connection;
        this.clazz = clazz;
        this.selectStmt = ReflectionHelper.createSelectStatementForClass(this.clazz);
        this.insertStmt = ReflectionHelper.createInsertStatementForClass(this.clazz);
        this.updateStmt = ReflectionHelper.createUpdateStatementForClass(this.clazz);
    }

    public void create(T objectData) throws SQLException, IllegalAccessException, NoSuchFieldException {
        Savepoint savePoint = connection.setSavepoint("savePointCreate");
        Class<T> clazz = (Class<T>) objectData.getClass();
        try (PreparedStatement pst = connection.prepareStatement(this.insertStmt,
                Statement.RETURN_GENERATED_KEYS)) {
            List<Field> fields = ReflectionHelper.getNonIdFields(clazz);
            for (int idx = 0; idx < fields.size(); idx++) {
                Field field = fields.get(idx);
                if (!Modifier.isPublic(field.getModifiers())) field.setAccessible(true);
                pst.setObject(idx + 1, field.get(objectData));
            }
            pst.executeUpdate();
            // установка id в объект
            Field idField = ReflectionHelper.getIdField(clazz);
            if (!Modifier.isPublic(idField.getModifiers())) idField.setAccessible(true);
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                idField.set(objectData, rs.getLong(1));
            }
        } catch (SQLException | IllegalAccessException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    public void update(T objectData) throws SQLException, IllegalAccessException, NoSuchFieldException {
        Savepoint savePoint = connection.setSavepoint("savePointUpdate");
        Class<T> clazz = (Class<T>) objectData.getClass();
        try (PreparedStatement pst = connection.prepareStatement(this.updateStmt,
                Statement.NO_GENERATED_KEYS)) {
            Field idField = ReflectionHelper.getIdField(clazz);
            if (!Modifier.isPublic(idField.getModifiers())) idField.setAccessible(true);
            List<Field> nonIdfields = ReflectionHelper.getNonIdFields(clazz);
            for (int idx = 0; idx < nonIdfields.size(); idx++) {
                Field field = nonIdfields.get(idx);
                if (!Modifier.isPublic(field.getModifiers())) field.setAccessible(true);
                pst.setObject(idx + 1, field.get(objectData));
            }
            pst.setObject(nonIdfields.size() + 1, idField.get(objectData));
            pst.executeUpdate();
        } catch (SQLException | IllegalAccessException | NoSuchFieldException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
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
                        // заполняем не статические поля, и не транзиентные
                        for (Field field : clazz.getDeclaredFields()) {
                            int modifiers = field.getModifiers();
                            if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;
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
}
