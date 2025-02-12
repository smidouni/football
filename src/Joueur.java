/**
 * Represents a player (Joueur) in an Equipe.
 * 
 * <p>
 * This class stores basic information about a player including id, name, first
 * name,
 * and jersey number. It also allows updating the player's data.
 * </p>
 * 
 * @version 1.0
 */
public class Joueur {
	private int id;
	private String nom;
	private String prenom;
	private int numeroMaillot;

	/**
	 * Constructs a Joueur with a specified id, last name, first name, and jersey
	 * number.
	 *
	 * @param id            the player id
	 * @param nom           the last name of the player
	 * @param prenom        the first name of the player
	 * @param numeroMaillot the jersey number
	 */
	public Joueur(int id, String nom, String prenom, int numeroMaillot) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.numeroMaillot = numeroMaillot;
	}

	/**
	 * Constructs a Joueur with a last name, first name, and jersey number.
	 *
	 * @param nom           the last name of the player
	 * @param prenom        the first name of the player
	 * @param numeroMaillot the jersey number
	 */
	public Joueur(String nom, String prenom, int numeroMaillot) {
		this.nom = nom;
		this.prenom = prenom;
		this.numeroMaillot = numeroMaillot;
	}

	/**
	 * Gets the player id.
	 *
	 * @return the player id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the player id.
	 *
	 * @param id the new player id.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the player's last name.
	 *
	 * @return the last name.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Gets the player's first name.
	 *
	 * @return the first name.
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Gets the player's jersey number.
	 *
	 * @return the jersey number.
	 */
	public int getNumeroMaillot() {
		return numeroMaillot;
	}

	/**
	 * Modifies the player's information.
	 *
	 * @param nom           the new last name.
	 * @param prenom        the new first name.
	 * @param numeroMaillot the new jersey number.
	 */
	public void modify(String nom, String prenom, int numeroMaillot) {
		this.nom = nom;
		this.prenom = prenom;
		this.numeroMaillot = numeroMaillot;
	}
}
