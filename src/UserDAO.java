import java.sql.*;

/**
 * Data Access Object (DAO) for user management.
 * 
 * <p>
 * This singleton class provides methods for user login and registration.
 * </p>
 * 
 * @version 1.0
 */
public class UserDAO {

    private static UserDAO instance;

    /**
     * Private constructor to prevent external instantiation.
     */
    private UserDAO() {
    }

    /**
     * Returns the singleton instance of UserDAO.
     *
     * @return the UserDAO instance.
     */
    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    /**
     * Checks if the provided username and password match a user in the database.
     *
     * @param username the username to check.
     * @param password the password to check.
     * @return true if the credentials are valid; false otherwise.
     */
    public boolean login(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        boolean connected = false;

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    connected = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connected;
    }

    /**
     * Registers a new user in the database.
     *
     * @param username the username for the new user.
     * @param password the password for the new user.
     */
    public void register(String username, String password) {
        String insertQuery = "INSERT INTO user (username, password) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(insertQuery)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
