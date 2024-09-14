package dao;

import connection.ConnectionFactory;
import model.Client;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
/**
 * Data Access Object (DAO) for managing client data in the database. This class extends {@code GenericDAO} and
 * provides specific methods for client-related database operations. It handles the CRUD operations for the {@code Client}
 * entity by interacting with a SQL database, utilizing {@code ConnectionFactory} to establish database connections.
 */
public class ClientDAO extends GenericDAO<Client> {

    /**
     * Constructs a new {@code ClientDAO} instance for managing {@code Client} entities, utilizing a generic DAO structure.
     */
    public ClientDAO() {
        super(Client.class);
    }
    /**
     * Retrieves a client by their ID from the database.
     * This method returns an {@code Optional} of {@code Client}, which will be empty if no client is found with the given ID.
     * @param clientId the unique identifier of the client to find
     * @return an {@code Optional} containing the found client or an empty {@code Optional} if no client is found
     */
    @Override
    public Optional<Client> findById(int clientId) {
        String SQL = "SELECT * FROM client WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, clientId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("email")
                );
                return Optional.of(client);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "ClientDAO:findById " + e.getMessage());
        }
        return Optional.empty();
    }
}

