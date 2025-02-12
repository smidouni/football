import java.util.List;

/**
 * Represents a day (Journee) of matches in the league.
 * 
 * <p>
 * This class holds a list of {@link Match} objects and provides a method to
 * simulate
 * the matches for the day.
 * </p>
 * 
 * @version 1.0
 */
public class Journee {
	List<Match> matchs;

	/**
	 * Constructs a Journee with the specified list of matches.
	 *
	 * @param matchs the list of {@link Match} objects.
	 */
	Journee(List<Match> matchs) {
		this.matchs = matchs;
	}

	/**
	 * Simulates all matches in this journee.
	 */
	void jouerJournee() {
		for (Match match : matchs) {
			match.simulerMatch();
		}
	}
}
