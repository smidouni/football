import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing the database connection.
 * 
 * <p>
 * This class loads the MySQL JDBC driver and provides a method to retrieve a
 * connection
 * to the specified database.
 * </p>
 * 
 * @version 1.0
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/football?useSSL=false&serverTimezone=UTC";
    private static final String LOGIN = "root";
    private static final String PASS = "";

    // Static block to load the JDBC driver.
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver", e);
        }
    }

    /**
     * Retrieves a connection to the database.
     *
     * @return a {@link Connection} object
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASS);
    }
}
