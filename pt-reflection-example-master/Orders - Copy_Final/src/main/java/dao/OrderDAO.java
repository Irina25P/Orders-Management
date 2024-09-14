package dao;

import connection.ConnectionFactory;
import model.Order;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
/**
 * Data Access Object (DAO) for handling database operations specific to {@code Order} entities.
 * This class provides functionalities for retrieving order details from the database. It extends
 * the {@code GenericDAO} class to utilize common CRUD operations while providing specifics for order-related queries.
 */
public class OrderDAO extends GenericDAO<Order> {
    /**
     * Constructs an {@code OrderDAO} instance with {@code Order.class} as the entity type for generic operations.
     */
    public OrderDAO() {
        super(Order.class);
    }
    /**
     * Retrieves an {@code Order} from the database by its ID.
     * This method queries the 'order' table to find a matching order record. If found, it constructs
     * an {@code Order} object and returns it wrapped in an {@code Optional}. If no order is found, it returns
     * an empty {@code Optional}.
     *
     * @param orderId the ID of the order to retrieve
     * @return an {@code Optional<Order>} containing the found order if it exists, otherwise an empty {@code Optional}
     */
    @Override
    public Optional<Order> findById(int orderId) {
        String SQL = "SELECT * FROM `order` WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getInt("clientId"),
                        rs.getInt("productId"),
                        rs.getInt("quantity")
                );
                return Optional.of(order);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "OrderDAO:findById " + e.getMessage());
        }
        return Optional.empty();
    }
}

