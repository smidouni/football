import java.util.List;

/**
 * Represents a team (Equipe) in the league.
 * 
 * <p>
 * This class manages the team’s properties, players, and points. It also
 * provides
 * functionality for creating a team and modifying its roster.
 * </p>
 * 
 * @version 1.0
 */
public class Equipe {
	int id;
	String nom;
	Ligue ligue;
	List<Joueur> joueurs;
	int points;

	/**
	 * Constructs an Equipe with a specified id, name, league, and points.
	 *
	 * @param id     the team identifier
	 * @param nom    the team name
	 * @param ligue  the {@link Ligue} to which the team belongs
	 * @param points the initial points for the team
	 */
	public Equipe(int id, String nom, Ligue ligue, int points) {
		this.id = id;
		this.nom = nom;
		this.ligue = ligue;
		populateJoueurs();
		this.points = points;
	}

	/**
	 * Constructs an Equipe with a specified name and league.
	 * The team starts with 0 points.
	 *
	 * @param nom   the team name
	 * @param ligue the {@link Ligue} to which the team belongs
	 */
	public Equipe(String nom, Ligue ligue) {
		this.nom = nom;
		this.ligue = ligue;
		this.points = 0;
	}

	/**
	 * Constructs an Equipe with only an id.
	 *
	 * @param id the team identifier
	 */
	public Equipe(int id) {
		this.id = id;
	}

	/**
	 * Creates a new Equipe in the database with a list of players.
	 *
	 * @param nom     the team name
	 * @param ligue   the {@link Ligue} to which the team belongs
	 * @param joueurs the list of players to add to the team
	 * @return the newly created {@link Equipe} with an assigned id and roster
	 */
	public static Equipe creerEquipe(String nom, Ligue ligue, List<Joueur> joueurs) {
		Equipe nouvelleEquipe = new Equipe(nom, ligue);

		int equipeId = EquipeDAO.getInstance().creerEquipe(nouvelleEquipe);
		nouvelleEquipe.setId(equipeId);

		JoueurDAO.getInstance().ajouterJoueurs(joueurs, nouvelleEquipe);
		nouvelleEquipe.modifyJoueurs(joueurs);

		return nouvelleEquipe;
	}

	/**
	 * Adds a new player to the team.
	 *
	 * @param nom           the last name of the player
	 * @param prenom        the first name of the player
	 * @param numeroMaillot the jersey number of the player
	 */
	public void ajouterJoueur(String nom, String prenom, int numeroMaillot) {
		Joueur nouveauJoueur = new Joueur(nom, prenom, numeroMaillot);
		int joueurId = JoueurDAO.getInstance().ajouterJoueur(nouveauJoueur, this);
		nouveauJoueur.setId(joueurId);
		joueurs.add(nouveauJoueur);
	}

	/**
	 * Modifies an existing player's information.
	 *
	 * @param joueur        the {@link Joueur} to modify
	 * @param nom           the new last name
	 * @param prenom        the new first name
	 * @param numeroMaillot the new jersey number
	 */
	public void modifierJoueur(Joueur joueur, String nom, String prenom, int numeroMaillot) {
		joueur.modify(nom, prenom, numeroMaillot);
		JoueurDAO.getInstance().modifierJoueur(joueur);
	}

	/**
	 * Removes a player from the team.
	 *
	 * @param joueur the {@link Joueur} to remove
	 */
	public void supprimerJoueur(Joueur joueur) {
		JoueurDAO.getInstance().supprimerJoueur(joueur);
		joueurs.remove(joueur);
	}

	/**
	 * Loads the list of players for this team from the database.
	 */
	public void populateJoueurs() {
		this.joueurs = JoueurDAO.getInstance().getJoueurs(this);
	}

	/**
	 * Gets the team's id.
	 *
	 * @return the team id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the team's id.
	 *
	 * @param id the new team id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the team name.
	 *
	 * @return the team name
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Sets the team name.
	 *
	 * @param nom the new team name
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Gets the {@link Ligue} to which the team belongs.
	 *
	 * @return the league
	 */
	public Ligue getLigue() {
		return ligue;
	}

	/**
	 * Gets the list of players.
	 *
	 * @return a list of {@link Joueur} objects
	 */
	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Updates the team’s player list.
	 *
	 * @param joueurs the new list of players
	 */
	public void modifyJoueurs(List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	/**
	 * Gets the team’s points.
	 *
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Adds points to the team.
	 *
	 * @param points the points to add
	 */
	public void addPoints(int points) {
		this.points += points;
	}
}
