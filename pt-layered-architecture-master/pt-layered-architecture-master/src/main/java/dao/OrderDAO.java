package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Order;

public class OrderDAO {

    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    private static final String INSERT_STATEMENT = "INSERT INTO `order` (clientId, productId, quantity) VALUES (?, ?, ?)";
    private static final String FIND_BY_ID_STATEMENT = "SELECT * FROM `order` WHERE id = ?";
    private static final String UPDATE_STATEMENT = "UPDATE `order` SET clientId = ?, productId = ?, quantity = ? WHERE id = ?";
    private static final String FIND_ALL_STATEMENT = "SELECT * FROM `order`";

    public static Order findById(int orderId) {
        Order toReturn = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_STATEMENT)) {
            statement.setInt(1, orderId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    toReturn = new Order(orderId, rs.getInt("clientId"), rs.getInt("productId"), rs.getInt("quantity"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:findById " + e.getMessage());
        }
        return toReturn;
    }

    public static int insert(Order order) {
        int insertedId = -1;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order.getClientId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    insertedId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
        }
        return insertedId;
    }

    public static void update(Order order) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STATEMENT)) {
            statement.setInt(1, order.getClientId());
            statement.setInt(2, order.getProductId());
            statement.setInt(3, order.getQuantity());
            statement.setInt(4, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:update " + e.getMessage());
        }
    }

    public static List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_STATEMENT);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                orders.add(new Order(rs.getInt("id"), rs.getInt("clientId"), rs.getInt("productId"), rs.getInt("quantity")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:findAll " + e.getMessage());
        }
        return orders;
    }
}
