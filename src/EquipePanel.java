import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Panel for viewing and managing a selected Equipe.
 * 
 * <p>
 * This panel allows users to change the team name, add, modify, and remove
 * players.
 * </p>
 * 
 * @version 1.0
 */
public class EquipePanel extends JPanel implements ActionListener {

    private JTextField equipeNameField;
    private JList<String> playersList;
    private DefaultListModel<String> playersListModel;
    private JButton validateNameButton;
    private JButton addPlayerButton;
    private JButton modifyPlayerButton;
    private JButton removePlayerButton;
    private JButton backButton;

    // The current equipe loaded in this panel.
    private Equipe currentEquipe;

    /**
     * Constructs an EquipePanel and initializes its UI components.
     */
    public EquipePanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(800, 800));

        // Top: Title
        JLabel titleLabel = new JLabel("Equipe Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Center: Equipe name and players list
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.DARK_GRAY);

        // Equipe name panel for changing the team name
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.setBackground(Color.DARK_GRAY);
        JLabel nameLabel = new JLabel("Equipe Name:");
        nameLabel.setForeground(Color.WHITE);
        equipeNameField = new JTextField(20);
        namePanel.add(nameLabel);
        namePanel.add(equipeNameField);
        validateNameButton = new JButton("Validate Name Change");
        validateNameButton.setBackground(new Color(60, 178, 43));
        validateNameButton.setForeground(Color.WHITE);
        validateNameButton.addActionListener(this);
        namePanel.add(validateNameButton);
        centerPanel.add(namePanel, BorderLayout.NORTH);

        // Players list panel
        playersListModel = new DefaultListModel<>();
        playersList = new JList<>(playersListModel);
        playersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playersList.setBackground(Color.WHITE);
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Players (ordered by jersey number)",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                null,
                Color.BLACK);
        JScrollPane playersScrollPane = new JScrollPane(playersList);
        playersScrollPane.setBorder(border);
        centerPanel.add(playersScrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setBackground(Color.DARK_GRAY);

        addPlayerButton = new JButton("Add Player");
        addPlayerButton.setBackground(new Color(60, 178, 43));
        addPlayerButton.setForeground(Color.WHITE);
        addPlayerButton.addActionListener(this);
        buttonsPanel.add(addPlayerButton);

        modifyPlayerButton = new JButton("Modify Selected Player");
        modifyPlayerButton.setBackground(new Color(60, 178, 43));
        modifyPlayerButton.setForeground(Color.WHITE);
        modifyPlayerButton.addActionListener(this);
        buttonsPanel.add(modifyPlayerButton);

        removePlayerButton = new JButton("Remove Selected Player");
        removePlayerButton.setBackground(new Color(60, 178, 43));
        removePlayerButton.setForeground(Color.WHITE);
        removePlayerButton.addActionListener(this);
        buttonsPanel.add(removePlayerButton);

        backButton = new JButton("Back to Ligue Panel");
        backButton.setBackground(new Color(60, 178, 43));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> MainWindow.getInstance().showPanel(MainWindow.LIGUE_PANEL));
        buttonsPanel.add(backButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Loads the given Equipe into the panel.
     *
     * @param equipe the {@link Equipe} to load into the panel.
     */
    public void loadEquipe(Equipe equipe) {
        if (equipe == null) {
            JOptionPane.showMessageDialog(this, "No equipe selected!");
            return;
        }
        this.currentEquipe = equipe;
        // Set the team name.
        equipeNameField.setText(equipe.getNom());

        // Clear and repopulate the players list.
        playersListModel.clear();
        List<Joueur> players = new ArrayList<>(equipe.getJoueurs());
        // Sort players by jersey number (ascending).
        players.sort((j1, j2) -> Integer.compare(j1.getNumeroMaillot(), j2.getNumeroMaillot()));
        for (Joueur joueur : players) {
            String playerInfo = joueur.getNumeroMaillot() + " - " + joueur.getPrenom() + " " + joueur.getNom();
            playersListModel.addElement(playerInfo);
        }
    }

    /**
     * Prompts the user for player information.
     *
     * @param title         the dialog title.
     * @param defaultPrenom default first name.
     * @param defaultNom    default last name.
     * @return a String array containing the first name and last name, or null if
     *         cancelled.
     */
    private String[] promptForPlayerInfo(String title, String defaultPrenom, String defaultNom) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel prenomLabel = new JLabel("First Name:");
        JTextField prenomField = new JTextField(defaultPrenom, 10);
        JLabel nomLabel = new JLabel("Last Name:");
        JTextField nomField = new JTextField(defaultNom, 10);
        panel.add(prenomLabel);
        panel.add(prenomField);
        panel.add(nomLabel);
        panel.add(nomField);

        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String prenom = prenomField.getText().trim();
            String nom = nomField.getText().trim();
            if (prenom.isEmpty() || nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Both fields must be filled out.");
                return null;
            }
            return new String[] { prenom, nom };
        }
        return null;
    }

    /**
     * Handles button click events for renaming the team, adding, modifying, or
     * removing players.
     *
     * @param e the ActionEvent generated by a button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == validateNameButton) {
            String newName = equipeNameField.getText().trim();
            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Equipe name cannot be empty.");
            } else {
                currentEquipe.setNom(newName);
                EquipeDAO.getInstance().modifierNomEquipe(currentEquipe);
                JOptionPane.showMessageDialog(this, "Equipe name changed to " + newName);
            }
        } else if (e.getSource() == addPlayerButton) {
            // Allow adding only if there are less than 11 players.
            if (currentEquipe.getJoueurs().size() >= 11) {
                JOptionPane.showMessageDialog(this, "Team is full. Maximum 11 players allowed.");
                return;
            }

            String[] info = promptForPlayerInfo("Add Player", "", "");
            if (info == null)
                return;
            String prenom = info[0];
            String nom = info[1];

            // Determine the smallest available jersey number (1 to 11).
            Set<Integer> takenNumbers = new HashSet<>();
            for (Joueur j : currentEquipe.getJoueurs()) {
                takenNumbers.add(j.getNumeroMaillot());
            }
            int assigned = -1;
            for (int i = 1; i <= 11; i++) {
                if (!takenNumbers.contains(i)) {
                    assigned = i;
                    break;
                }
            }
            if (assigned == -1) {
                JOptionPane.showMessageDialog(this, "No available jersey numbers.");
                return;
            }

            // Add the new player using the Equipe method.
            currentEquipe.ajouterJoueur(nom, prenom, assigned);
            loadEquipe(currentEquipe);
        } else if (e.getSource() == modifyPlayerButton) {
            int selectedIndex = playersList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a player to modify.");
                return;
            }
            // Create a sorted copy of players (matching display order).
            List<Joueur> players = new ArrayList<>(currentEquipe.getJoueurs());
            players.sort((j1, j2) -> Integer.compare(j1.getNumeroMaillot(), j2.getNumeroMaillot()));
            Joueur toModify = players.get(selectedIndex);

            // Prompt with default values set to the current player's names.
            String[] info = promptForPlayerInfo("Modify Player", toModify.getPrenom(), toModify.getNom());
            if (info == null)
                return;
            String newPrenom = info[0];
            String newNom = info[1];

            // Modify the player.
            currentEquipe.modifierJoueur(toModify, newNom, newPrenom, toModify.getNumeroMaillot());
            loadEquipe(currentEquipe);
        } else if (e.getSource() == removePlayerButton) {
            int selectedIndex = playersList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a player to remove.");
                return;
            }
            List<Joueur> players = new ArrayList<>(currentEquipe.getJoueurs());
            players.sort((j1, j2) -> Integer.compare(j1.getNumeroMaillot(), j2.getNumeroMaillot()));
            Joueur toRemove = players.get(selectedIndex);
            currentEquipe.supprimerJoueur(toRemove);
            loadEquipe(currentEquipe);
        }
    }
}
