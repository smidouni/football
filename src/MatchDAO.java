import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing Match records in the database.
 * 
 * <p>
 * This singleton class provides methods to retrieve, create, and update
 * matches.
 * </p>
 * 
 * @version 1.0
 */
public class MatchDAO {

    private static MatchDAO instance;

    /**
     * Private constructor to prevent external instantiation.
     */
    private MatchDAO() {
    }

    /**
     * Returns the singleton instance of MatchDAO.
     *
     * @return the MatchDAO instance.
     */
    public static MatchDAO getInstance() {
        if (instance == null) {
            instance = new MatchDAO();
        }
        return instance;
    }

    /**
     * Retrieves all matches from the database.
     *
     * @return a list of {@link Match} objects.
     */
    public List<Match> getMatches() {
        List<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM `match`;";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int equipe1Id = rs.getInt("equipe1Id");
                int equipe2Id = rs.getInt("equipe2Id");
                int nbButsEquipe1 = rs.getInt("nbButsEquipe1");
                int nbButsEquipe2 = rs.getInt("nbButsEquipe2");

                matches.add(new Match(id, new Equipe(equipe1Id), new Equipe(equipe2Id), nbButsEquipe1, nbButsEquipe2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matches;
    }

    /**
     * Creates a new match record in the database.
     *
     * @param match the {@link Match} to create.
     * @return the generated match id.
     */
    public int creerMatch(Match match) {
        String query = "INSERT INTO `match` (equipe1Id, equipe2Id, nbButsEquipe1, nbButsEquipe2) VALUES (?, ?, ?, ?)";
        int idMatch = 0;

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, match.getEquipe1().getId());
            ps.setInt(2, match.getEquipe2().getId());
            ps.setInt(3, match.getNbButsEquipe1());
            ps.setInt(4, match.getNbButsEquipe2());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idMatch = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idMatch;
    }

    /**
     * Updates the number of goals for a match in the database.
     *
     * @param match the {@link Match} with updated scores.
     */
    public void modifierButsMatch(Match match) {
        String query = "UPDATE `match` SET nbButsEquipe1 = ?, nbButsEquipe2 = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, match.getNbButsEquipe1());
            ps.setInt(2, match.getNbButsEquipe2());
            ps.setInt(3, match.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
