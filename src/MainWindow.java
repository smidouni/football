import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Main application window using a CardLayout to switch between panels.
 * 
 * <p>
 * This class is a singleton that holds the current league and manages
 * navigation
 * between the various UI panels (login, register, league, team, match, etc.).
 * </p>
 * 
 * @version 1.0
 */
public class MainWindow extends JFrame {
    private static MainWindow instance;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, Component> panelMap; // Panels stored by key

    // Panel names (keys)
    public static final String LOGIN_PANEL = "LOGIN";
    public static final String REGISTER_PANEL = "REGISTER";
    public static final String LIGUE_PANEL = "LIGUE";
    public static final String CREATE_EQUIPE_PANEL = "CREATE_EQUIPE";
    public static final String EQUIPE_PANEL = "EQUIPE";
    public static final String MATCH_PANEL = "MATCH";

    // Centrally maintained current Ligue.
    private Ligue currentLigue;

    /**
     * Constructs the MainWindow and initializes all panels.
     */
    public MainWindow() {
        instance = this;
        setTitle("Soccer League App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.DARK_GRAY);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        panelMap = new HashMap<>();

        // Create panels.
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.setName(LOGIN_PANEL);
        RegisterPanel registerPanel = new RegisterPanel();
        registerPanel.setName(REGISTER_PANEL);
        LiguePanel liguePanel = new LiguePanel();
        liguePanel.setName(LIGUE_PANEL);
        CreateEquipePanel createEquipePanel = new CreateEquipePanel();
        createEquipePanel.setName(CREATE_EQUIPE_PANEL);
        EquipePanel equipePanel = new EquipePanel();
        equipePanel.setName(EQUIPE_PANEL);
        MatchPanel matchPanel = new MatchPanel();
        matchPanel.setName(MATCH_PANEL);

        // Store panels in the map.
        panelMap.put(LOGIN_PANEL, loginPanel);
        panelMap.put(REGISTER_PANEL, registerPanel);
        panelMap.put(LIGUE_PANEL, liguePanel);
        panelMap.put(CREATE_EQUIPE_PANEL, createEquipePanel);
        panelMap.put(EQUIPE_PANEL, equipePanel);
        panelMap.put(MATCH_PANEL, matchPanel);

        // Add panels to the main panel.
        mainPanel.add(loginPanel, LOGIN_PANEL);
        mainPanel.add(registerPanel, REGISTER_PANEL);
        mainPanel.add(liguePanel, LIGUE_PANEL);
        mainPanel.add(createEquipePanel, CREATE_EQUIPE_PANEL);
        mainPanel.add(equipePanel, EQUIPE_PANEL);
        mainPanel.add(matchPanel, MATCH_PANEL);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Returns the singleton instance of MainWindow.
     *
     * @return the MainWindow instance.
     */
    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    /**
     * Switches the displayed panel based on the provided key.
     *
     * @param panelName the key corresponding to the panel to display.
     */
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
        // If switching to LiguePanel, update its content.
        if (panelName.equals(LIGUE_PANEL)) {
            Component panel = getPanel(LIGUE_PANEL);
            if (panel instanceof LiguePanel) {
                ((LiguePanel) panel).updateContent();
            }
        }
    }

    /**
     * Retrieves a panel by its key.
     *
     * @param panelName the key of the panel.
     * @return the {@link Component} corresponding to the panel.
     */
    public Component getPanel(String panelName) {
        return panelMap.get(panelName);
    }

    /**
     * Sets the current league.
     *
     * @param ligue the {@link Ligue} to set as current.
     */
    public void setCurrentLigue(Ligue ligue) {
        this.currentLigue = ligue;
    }

    /**
     * Gets the current league.
     *
     * @return the current {@link Ligue}.
     */
    public Ligue getCurrentLigue() {
        return currentLigue;
    }

    /**
     * Main method to launch the application.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}
