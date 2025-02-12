import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing Equipe records in the database.
 * 
 * <p>
 * This singleton class provides methods to create, read, and update Equipe
 * data.
 * </p>
 * 
 * @version 1.0
 */
public class EquipeDAO {
    private static EquipeDAO instance; // Singleton instance

    /**
     * Private constructor to prevent instantiation.
     */
    private EquipeDAO() {
    }

    /**
     * Returns the singleton instance of EquipeDAO.
     *
     * @return the EquipeDAO instance.
     */
    public static EquipeDAO getInstance() {
        if (instance == null) {
            instance = new EquipeDAO();
        }
        return instance;
    }

    /**
     * Retrieves all teams belonging to a given league.
     *
     * @param ligue the {@link Ligue} for which teams are to be retrieved.
     * @return a list of {@link Equipe} objects.
     */
    public List<Equipe> getEquipes(Ligue ligue) {
        String query = "SELECT * FROM equipe WHERE ligue = ?";
        List<Equipe> equipes = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, ligue.getNom());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    int points = rs.getInt("points");
                    Equipe equipe = new Equipe(id, nom, ligue, points);
                    equipes.add(equipe);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipes;
    }

    /**
     * Creates a new Equipe in the database.
     *
     * @param equipe the {@link Equipe} to create.
     * @return the generated id for the new team.
     */
    public int creerEquipe(Equipe equipe) {
        String query = "INSERT INTO equipe (nom, ligue, points) VALUES (?, ?, ?)";
        int idEquipe = 0;

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, equipe.getNom());
            ps.setString(2, equipe.getLigue().getNom());
            ps.setInt(3, equipe.getPoints());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idEquipe = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idEquipe;
    }

    /**
     * Updates the name of an existing Equipe in the database.
     *
     * @param equipe the {@link Equipe} with the new name.
     */
    public void modifierNomEquipe(Equipe equipe) {
        String query = "UPDATE equipe SET nom = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, equipe.getNom());
            ps.setInt(2, equipe.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the points of an existing Equipe in the database.
     *
     * @param equipe the {@link Equipe} whose points are to be updated.
     */
    public void modifierPointsEquipe(Equipe equipe) {
        String query = "UPDATE equipe SET points = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, equipe.getPoints());
            ps.setInt(2, equipe.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
