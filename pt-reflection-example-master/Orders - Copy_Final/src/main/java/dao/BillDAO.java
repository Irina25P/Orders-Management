package dao;

import connection.ConnectionFactory;
import model.Bill;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Provides the data access object (DAO) for handling all database operations
 * related to the {@code Bill} entity. This includes operations such as inserting new bills
 * and retrieving all bills from the database.
 */
public class BillDAO {
    private static final String INSERT_BILL = "INSERT INTO bill (orderId, clientId, amount, dateTime) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_BILLS = "SELECT * FROM bill";
    /**
     * Inserts a new bill into the database. This method sets up a connection to the database,
     * prepares a statement for execution, and fills it with bill data.
     *
     * @param bill the {@code Bill} object containing the data to be inserted
     */
    public static void insert(Bill bill) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BILL)) {
            preparedStatement.setInt(1, bill.orderId());
            preparedStatement.setInt(2, bill.clientId());
            preparedStatement.setDouble(3, bill.amount());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(bill.dateTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Retrieves all bills from the database. This method sets up a connection, prepares a statement,
     * executes it and builds a list of {@code Bill} objects from the results.
     *
     * @return a list of {@code Bill} objects representing all bills stored in the database
     */
    public static List<Bill> findAll() {
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BILLS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int orderId = resultSet.getInt("orderId");
                int clientId = resultSet.getInt("clientId");
                double amount = resultSet.getDouble("amount");
                LocalDateTime dateTime = resultSet.getTimestamp("dateTime").toLocalDateTime();
                bills.add(new Bill(orderId, clientId, amount, dateTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }
}
