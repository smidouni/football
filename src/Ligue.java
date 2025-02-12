import java.util.ArrayList;
import java.util.List;

/**
 * Represents a league (Ligue) in the soccer application.
 * 
 * <p>
 * This class manages the teams and matches of the league, including loading
 * data from
 * the database and sorting teams by points.
 * </p>
 * 
 * @version 1.0
 */
public class Ligue {
	String nom;
	List<Equipe> equipes;
	List<Match> matches;

	/**
	 * Constructs a Ligue with the specified name and populates its teams and
	 * matches.
	 *
	 * @param nom the name of the league
	 */
	public Ligue(String nom) {
		this.nom = nom;
		populate();
	}

	/**
	 * Populates the league data including teams and matches.
	 */
	private void populate() {
		populateEquipes();
		populateMatches();
	}

	/**
	 * Populates the list of teams from the database.
	 */
	private void populateEquipes() {
		equipes = EquipeDAO.getInstance().getEquipes(this);
	}

	/**
	 * Populates the list of matches by filtering from all matches in the database.
	 */
	private void populateMatches() {
		List<Match> allMatches = MatchDAO.getInstance().getMatches();
		List<Match> filteredMatches = new ArrayList<>();

		for (Match match : allMatches) {
			// Check if either team in the match is part of the league.
			boolean equipe1InLigue = equipes.stream().anyMatch(equipe -> equipe.getId() == match.getEquipe1().getId());
			boolean equipe2InLigue = equipes.stream().anyMatch(equipe -> equipe.getId() == match.getEquipe2().getId());

			if (equipe1InLigue || equipe2InLigue) {
				// Replace the match's team references with the proper Equipe objects from the
				// league.
				for (Equipe equipe : equipes) {
					if (equipe.getId() == match.getEquipe1().getId()) {
						match.equipe1 = equipe;
					}
					if (equipe.getId() == match.getEquipe2().getId()) {
						match.equipe2 = equipe;
					}
				}
				filteredMatches.add(match);
			}
		}

		matches = filteredMatches;
	}

	/**
	 * Gets the league name.
	 *
	 * @return the league name.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Gets the list of teams in the league, sorted by points.
	 *
	 * @return a sorted list of {@link Equipe} objects.
	 */
	public List<Equipe> getEquipes() {
		sortEquipes();
		return equipes;
	}

	/**
	 * Gets the list of matches in the league.
	 *
	 * @return a list of {@link Match} objects.
	 */
	public List<Match> getMatches() {
		return matches;
	}

	/**
	 * Updates the list of teams.
	 *
	 * @param equipes the new list of teams.
	 */
	public void modifyEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}

	/**
	 * Removes a team from the league.
	 *
	 * @param equipe the {@link Equipe} to remove.
	 */
	public void removeEquipes(Equipe equipe) {
		equipes.remove(equipe);
	}

	/**
	 * Sorts the teams in descending order of points.
	 */
	public void sortEquipes() {
		equipes.sort((equipe1, equipe2) -> Integer.compare(equipe2.points, equipe1.points));
	}
}
