package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Manages the creation of database connections using the singleton design pattern.
 * This class ensures that all connections are created using the specified driver,
 * URL, user credentials, and handles any exceptions related to database connectivity.
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/ordersdb";
    private static final String USER = "root";
    private static final String PASS = "SahMath52Yri#";
    private static ConnectionFactory singleInstance = new ConnectionFactory();
    /**
     * Private constructor to prevent instantiation from outside this class.
     * Loads the database driver class.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates a new database connection using the predefined credentials.
     *
     * @return A new connection object
     * @throws RuntimeException if a connection cannot be established
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to create a database connection", e);
            throw new RuntimeException(e);
        }
        return connection;
    }
    /**
     * Provides a global point of access to the singleton instance of the {@code ConnectionFactory}.
     * Returns a new connection instance each time it's called.
     * @return a connection to the database
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }
}
