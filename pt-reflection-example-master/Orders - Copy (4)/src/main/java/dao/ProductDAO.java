package dao;

import connection.ConnectionFactory;
import model.Product;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;

public class ProductDAO extends GenericDAO<Product> {

    public ProductDAO() {
        super(Product.class);
    }

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
