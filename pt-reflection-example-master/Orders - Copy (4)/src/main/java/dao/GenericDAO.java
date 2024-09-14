package dao;

import connection.ConnectionFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GenericDAO<T> {
    private Class<T> type;
    protected static final Logger LOGGER = Logger.getLogger(GenericDAO.class.getName());

    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    private String getTableName() {
        String className = type.getSimpleName().toLowerCase();
        switch (className) {
            case "client":
                return "client";  // as in your database
            case "product":
                return "product"; // as in your database
            case "order":
                return "order";   // as in your database
            default:
                throw new IllegalArgumentException("Unknown class for DAO operations");
        }
    }

    public Optional<T> findById(int id) {
        T instance = null;
        String SQL = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = resultSet.getObject(field.getName());
                    if (value instanceof BigDecimal && field.getType().equals(double.class)) {
                        field.set(instance, ((BigDecimal) value).doubleValue());
                    } else {
                        field.set(instance, value);
                    }
                }
                return Optional.of(instance);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "GenericDAO:findById " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        String SQL = "SELECT * FROM " + getTableName();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                try {
                    T instance = type.getDeclaredConstructor().newInstance();
                    for (Field field : type.getDeclaredFields()) {
                        field.setAccessible(true);
                        Object value = resultSet.getObject(field.getName());
                        field.set(instance, value);
                    }
                    list.add(instance);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    LOGGER.log(Level.SEVERE, "Error creating instance of " + type.getSimpleName(), e);
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Error setting field values for " + type.getSimpleName(), e);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "GenericDAO:findAll " + e.getMessage(), e);
        }
        return list;
    }

    public int insert(T obj) {
        String columns = "";
        String values = "";
        int result = 0;
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (!columns.isEmpty()) columns += ", ";
            columns += field.getName();

            if (!values.isEmpty()) values += ", ";
            values += "?";
        }
        String tableName = getTableName();
        if ("order".equals(tableName.toLowerCase())) {
            tableName = "`order`";  // Use backticks for MySQL reserved keyword
        }
        String SQL = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                statement.setObject(i++, field.get(obj));
            }
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    result = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "GenericDAO:insert " + e.getMessage());
            throw new RuntimeException("Database insert error: " + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Access error setting field values: " + e.getMessage(), e);
        }
        return result;
    }


    public void update(T obj) {
        String setClause = "";
        int id = 0;
        try {
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getName().equals("id")) {
                    id = (int) field.get(obj);
                    continue;
                }
                if (!setClause.isEmpty()) setClause += ", ";
                setClause += field.getName() + " = ?";
            }
        } catch (IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "GenericDAO:update", e);
        }

        String SQL = "UPDATE " + getTableName() + " SET " + setClause + " WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            int i = 1;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.getName().equals("id")) {
                    statement.setObject(i++, field.get(obj));
                }
            }
            statement.setInt(i, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "GenericDAO:update " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        String SQL = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "GenericDAO:delete " + e.getMessage());
        }
    }
}
