package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;

public class ClientDAO {

	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
	private static final String INSERT_STATEMENT = "INSERT INTO client (name, address, email) VALUES (?, ?, ?)";
	private static final String UPDATE_STATEMENT = "UPDATE client SET name = ?, address = ?, email = ? WHERE id = ?";
	private static final String DELETE_STATEMENT = "DELETE FROM client WHERE id = ?";
	private static final String FIND_ALL_STATEMENT = "SELECT * FROM client";
	private static final String FIND_BY_ID_STATEMENT = "SELECT * FROM client WHERE id = ?";

	public static Client findById(int clientId) {
		Client toReturn = null;

		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_STATEMENT)) {
			statement.setInt(1, clientId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				toReturn = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("email"));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:findById " + e.getMessage());
		}
		return toReturn;
	}

	public static int insert(Client client) {
		int insertedId = -1;
		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, client.getName());
			statement.setString(2, client.getAddress());
			statement.setString(3, client.getEmail());
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
		}
		return insertedId;
	}

	public static void update(Client client) {
		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement statement = connection.prepareStatement(UPDATE_STATEMENT)) {
			statement.setString(1, client.getName());
			statement.setString(2, client.getAddress());
			statement.setString(3, client.getEmail());
			statement.setInt(4, client.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
		}
	}

	public static void delete(int id) {
		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement statement = connection.prepareStatement(DELETE_STATEMENT)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
		}
	}

	public static List<Client> findAll() {
		List<Client> clients = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_ALL_STATEMENT);
			 ResultSet rs = statement.executeQuery()) {
			while (rs.next()) {
				clients.add(new Client(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("email")));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:findAll " + e.getMessage());
		}
		return clients;
	}
}
