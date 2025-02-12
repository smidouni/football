import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing Joueur records in the database.
 * 
 * <p>
 * This singleton class provides methods to retrieve, add, modify, and delete
 * players.
 * </p>
 * 
 * @version 1.0
 */
public class JoueurDAO {

    private static JoueurDAO instance; // Singleton instance

    /**
     * Private constructor to prevent external instantiation.
     */
    private JoueurDAO() {
    }

    /**
     * Returns the singleton instance of JoueurDAO.
     *
     * @return the JoueurDAO instance.
     */
    public static JoueurDAO getInstance() {
        if (instance == null) {
            instance = new JoueurDAO();
        }
        return instance;
    }

    /**
     * Retrieves the list of players for a given Equipe from the database.
     *
     * @param equipe the {@link Equipe} whose players are to be retrieved.
     * @return a list of {@link Joueur} objects.
     */
    public List<Joueur> getJoueurs(Equipe equipe) {
        String query = "SELECT * FROM joueur WHERE equipe_id = ?";
        List<Joueur> joueurs = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, equipe.getId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    int numeroMaillot = rs.getInt("numeroMaillot");

                    Joueur joueur = new Joueur(id, nom, prenom, numeroMaillot);
                    joueurs.add(joueur);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return joueurs;
    }

    /**
     * Adds a new player to the database for a given Equipe.
     *
     * @param joueur the {@link Joueur} to add.
     * @param equipe the {@link Equipe} to which the player belongs.
     * @return the generated player id.
     */
    public int ajouterJoueur(Joueur joueur, Equipe equipe) {
        String query = "INSERT INTO joueur (nom, prenom, numeroMaillot, equipe_id) VALUES (?, ?, ?, ?)";
        int idJoueur = 0;

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, joueur.getNom());
            ps.setString(2, joueur.getPrenom());
            ps.setInt(3, joueur.getNumeroMaillot());
            ps.setInt(4, equipe.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idJoueur = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idJoueur;
    }

    /**
     * Adds multiple players to the database for a given Equipe using batch
     * processing.
     *
     * @param joueurs the list of {@link Joueur} objects to add.
     * @param equipe  the {@link Equipe} to which the players belong.
     * @return a list of generated player ids.
     */
    public List<Integer> ajouterJoueurs(List<Joueur> joueurs, Equipe equipe) {
        String query = "INSERT INTO joueur (nom, prenom, numeroMaillot, equipe_id) VALUES (?, ?, ?, ?)";
        List<Integer> generatedIds = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            con.setAutoCommit(false); // Use a transaction

            // Prepare the batch
            for (Joueur joueur : joueurs) {
                ps.setString(1, joueur.getNom());
                ps.setString(2, joueur.getPrenom());
                ps.setInt(3, joueur.getNumeroMaillot());
                ps.setInt(4, equipe.getId());
                ps.addBatch();
            }

            // Execute the batch
            ps.executeBatch();

            // Retrieve generated keys
            try (ResultSet rs = ps.getGeneratedKeys()) {
                int index = 0;
                while (rs.next()) {
                    int id = rs.getInt(1);
                    generatedIds.add(id);

                    // Optionally assign the generated id to the corresponding Joueur
                    if (index < joueurs.size()) {
                        joueurs.get(index).setId(id);
                    }
                    index++;
                }
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return generatedIds;
    }

    /**
     * Modifies an existing player's information in the database.
     *
     * @param joueur the {@link Joueur} to modify.
     */
    public void modifierJoueur(Joueur joueur) {
        String query = "UPDATE joueur SET nom = ?, prenom = ?, numeroMaillot = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, joueur.getNom());
            ps.setString(2, joueur.getPrenom());
            ps.setInt(3, joueur.getNumeroMaillot());
            ps.setInt(4, joueur.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a player from the database.
     *
     * @param joueur the {@link Joueur} to delete.
     */
    public void supprimerJoueur(Joueur joueur) {
        String query = "DELETE FROM joueur WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, joueur.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
