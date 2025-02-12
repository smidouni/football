/**
 * Entry point for the Soccer League Application.
 * 
 * <p>
 * This class initializes the application by launching the main window.
 * </p>
 * 
 * @version 1.0
 */
public class Main {
	/**
	 * The main method which starts the application.
	 *
	 * @param args command-line arguments (not used).
	 */
	public static void main(String[] args) {
		// Launch the MainWindow on the Event Dispatch Thread.
		javax.swing.SwingUtilities.invokeLater(() -> new MainWindow());
	}
}
