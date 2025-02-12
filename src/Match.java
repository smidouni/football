/**
 * Represents a soccer match between two teams.
 * 
 * <p>
 * This class holds details of the match such as the teams involved, the number
 * of goals,
 * and provides methods to simulate and save the match result.
 * </p>
 * 
 * @version 1.0
 */
public class Match {
	int id;
	Equipe equipe1;
	Equipe equipe2;
	int nbButsEquipe1;
	int nbButsEquipe2;

	/**
	 * Constructs a Match with the specified parameters.
	 *
	 * @param id            the match id
	 * @param equipe1       the first team
	 * @param equipe2       the second team
	 * @param nbButsEquipe1 the number of goals scored by the first team
	 * @param nbButsEquipe2 the number of goals scored by the second team
	 */
	public Match(int id, Equipe equipe1, Equipe equipe2, int nbButsEquipe1, int nbButsEquipe2) {
		this.id = id;
		this.equipe1 = equipe1;
		this.equipe2 = equipe2;
		this.nbButsEquipe1 = nbButsEquipe1;
		this.nbButsEquipe2 = nbButsEquipe2;
	}

	/**
	 * Constructs a Match between two teams.
	 * The match is initialized as not played (-1 goals for both teams).
	 *
	 * @param equipe1 the first team
	 * @param equipe2 the second team
	 */
	public Match(Equipe equipe1, Equipe equipe2) {
		this.equipe1 = equipe1;
		this.equipe2 = equipe2;
		this.nbButsEquipe1 = -1;
		this.nbButsEquipe2 = -1;
	}

	/**
	 * Creates a new Match in the database between two teams.
	 *
	 * @param equipe1 the first team
	 * @param equipe2 the second team
	 * @return the newly created {@link Match} with an assigned id.
	 */
	public static Match creerMatch(Equipe equipe1, Equipe equipe2) {
		Match nouveauMatch = new Match(equipe1, equipe2);
		// Create the match in the database and update its id.
		int idMatch = MatchDAO.getInstance().creerMatch(nouveauMatch);
		nouveauMatch.setId(idMatch);
		return nouveauMatch;
	}

	/**
	 * Simulates the match by generating random goals for each team and awarding
	 * points.
	 * The winning team receives 3 points; in case of a draw, both teams receive 1
	 * point.
	 */
	public void simulerMatch() {
		nbButsEquipe1 = (int) (Math.random() * 3); // generates 0, 1, or 2 goals
		nbButsEquipe2 = (int) (Math.random() * 3);

		if (nbButsEquipe1 > nbButsEquipe2) {
			equipe1.addPoints(3);
		} else if (nbButsEquipe1 < nbButsEquipe2) {
			equipe2.addPoints(3);
		} else {
			equipe1.addPoints(1);
			equipe2.addPoints(1);
		}
	}

	/**
	 * Saves the match result by updating team points and match scores in the
	 * database.
	 */
	public void saveMatch() {
		EquipeDAO.getInstance().modifierPointsEquipe(equipe1);
		EquipeDAO.getInstance().modifierPointsEquipe(equipe2);
		MatchDAO.getInstance().modifierButsMatch(this);
	}

	/**
	 * Gets the match id.
	 *
	 * @return the match id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the match id.
	 *
	 * @param id the new match id.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the number of goals scored by the first team.
	 *
	 * @return the goals for equipe1.
	 */
	public int getNbButsEquipe1() {
		return nbButsEquipe1;
	}

	/**
	 * Gets the number of goals scored by the second team.
	 *
	 * @return the goals for equipe2.
	 */
	public int getNbButsEquipe2() {
		return nbButsEquipe2;
	}

	/**
	 * Gets the first team.
	 *
	 * @return the first {@link Equipe}.
	 */
	public Equipe getEquipe1() {
		return equipe1;
	}

	/**
	 * Gets the second team.
	 *
	 * @return the second {@link Equipe}.
	 */
	public Equipe getEquipe2() {
		return equipe2;
	}
}
