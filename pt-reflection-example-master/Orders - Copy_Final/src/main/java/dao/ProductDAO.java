package dao;

import connection.ConnectionFactory;
import model.Product;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
/**
 * Data Access Object (DAO) for handling database operations for the {@code Product} entity.
 * This class extends {@code GenericDAO<Product>} to use generic CRUD operations and provides specific implementations
 * for product-related database access. It is designed to manage the retrieval, insertion, updating, and deletion of
 * product records in a SQL database.
 */
public class ProductDAO extends GenericDAO<Product> {

    /**
     * Constructs a {@code ProductDAO} instance with {@code Product.class} as the entity type for generic operations.
     */
    public ProductDAO() {
        super(Product.class);
    }
    /**
     * Retrieves a {@code Product} from the database by its ID.
     * This method queries the 'product' table to find a matching product record. If found, it constructs
     * a {@code Product} object with data retrieved from the database and returns it wrapped in an {@code Optional}.
     * If no product is found, it returns an empty {@code Optional}.
     * @param productId the ID of the product to retrieve
     * @return an {@code Optional<Product>} containing the found product if it exists, otherwise an empty {@code Optional}
     */
    public Optional<Product> findById(int productId) {
        String SQL = "SELECT * FROM product WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, productId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock")
                );
                return Optional.of(product);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding product by ID", e);
        }
        return Optional.empty();
    }
}
