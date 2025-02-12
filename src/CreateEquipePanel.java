import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Panel for creating a new Equipe (team).
 * 
 * <p>
 * This panel allows the user to input a team name and select up to 11 players
 * from a list of randomly generated candidate players. Once the form is
 * validated,
 * the new team is created in the database.
 * </p>
 * 
 * @version 1.0
 */
public class CreateEquipePanel extends JPanel implements ActionListener {

    private JTextField equipeNameField;
    private JList<String> candidatesList;
    private DefaultListModel<String> candidatesListModel;
    private JButton validateButton;
    private JButton cancelButton;

    // Candidate arrays for generating potential player names.
    private static final String[] NOM_CANDIDATES = {
            "Ronaldo", "Messi", "Neymar", "Benzema", "Kane",
            "Lewandowski", "Hazard", "Suarez", "Griezmann", "Mbappe"
    };
    private static final String[] PRENOM_CANDIDATES = {
            "Cristiano", "Lionel", "Luis", "Karim", "Harry",
            "Robert", "Eden", "Antoine", "Kylian", "Sergio"
    };
    private static final int NUM_CANDIDATES = 15;

    /**
     * Constructs a CreateEquipePanel and initializes its UI components.
     */
    public CreateEquipePanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(800, 800));

        // Title at the top
        JTextField titleLabel = new JTextField("Create New Equipe");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setEditable(false);
        titleLabel.setBackground(Color.DARK_GRAY);
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Center panel setup
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.DARK_GRAY);

        // Equipe name input panel
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.setBackground(Color.DARK_GRAY);
        JTextField nameLabel = new JTextField("Equipe Name:");
        nameLabel.setEditable(false);
        nameLabel.setBackground(Color.DARK_GRAY);
        nameLabel.setForeground(Color.WHITE);
        equipeNameField = new JTextField(20);
        namePanel.add(nameLabel);
        namePanel.add(equipeNameField);
        centerPanel.add(namePanel, BorderLayout.NORTH);

        // Generate candidate players list by randomly pairing first and last names.
        candidatesListModel = new DefaultListModel<>();
        Set<String> candidatesSet = new HashSet<>();
        Random rand = new Random();
        while (candidatesSet.size() < NUM_CANDIDATES) {
            String prenom = PRENOM_CANDIDATES[rand.nextInt(PRENOM_CANDIDATES.length)];
            String nom = NOM_CANDIDATES[rand.nextInt(NOM_CANDIDATES.length)];
            candidatesSet.add(prenom + " " + nom);
        }
        for (String candidate : candidatesSet) {
            candidatesListModel.addElement(candidate);
        }
        candidatesList = new JList<>(candidatesListModel);
        candidatesList.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane candidatesScrollPane = new JScrollPane(candidatesList);
        candidatesScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Select Players (max 11)",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                null,
                Color.WHITE));
        centerPanel.add(candidatesScrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel with Validate and Cancel buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setBackground(Color.DARK_GRAY);
        validateButton = new JButton("Validate");
        validateButton.setBackground(new Color(60, 178, 43));
        validateButton.setForeground(Color.WHITE);
        validateButton.addActionListener(this);
        buttonsPanel.add(validateButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(60, 178, 43));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> MainWindow.getInstance().showPanel(MainWindow.LIGUE_PANEL));
        buttonsPanel.add(cancelButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Handles action events for the panel’s buttons.
     *
     * @param e the ActionEvent generated by a button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String equipeName = equipeNameField.getText().trim();
        int[] selectedIndices = candidatesList.getSelectedIndices();
        if (equipeName.isEmpty() || selectedIndices.length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter an equipe name and select at least one player.");
            return;
        }
        if (selectedIndices.length > 11) {
            JOptionPane.showMessageDialog(this, "You can select a maximum of 11 players.");
            return;
        }

        // Retrieve the current Ligue from MainWindow.
        Ligue currentLigue = MainWindow.getInstance().getCurrentLigue();
        if (currentLigue == null) {
            JOptionPane.showMessageDialog(this, "No current league available!");
            return;
        }

        // Build a list of new players based on selected candidates.
        List<Joueur> selectedPlayers = new ArrayList<>();
        for (int i = 0; i < selectedIndices.length; i++) {
            String candidate = candidatesListModel.getElementAt(selectedIndices[i]);
            String[] parts = candidate.split(" ");
            if (parts.length < 2)
                continue;
            String prenom = parts[0];
            String nom = parts[1];
            int jerseyNumber = i + 1;
            Joueur joueur = new Joueur(nom, prenom, jerseyNumber);
            selectedPlayers.add(joueur);
        }

        // Create the new Equipe using the static method in Equipe.
        Equipe newEquipe = Equipe.creerEquipe(equipeName, currentLigue, selectedPlayers);

        // Build a summary string to display.
        StringBuilder summary = new StringBuilder("Equipe '" + newEquipe.getNom() + "' created with players:\n");
        List<Joueur> joueurs = newEquipe.getJoueurs();
        joueurs.sort((j1, j2) -> Integer.compare(j1.getNumeroMaillot(), j2.getNumeroMaillot()));
        for (Joueur j : joueurs) {
            summary.append(j.getNumeroMaillot())
                    .append(" - ")
                    .append(j.getPrenom())
                    .append(" ")
                    .append(j.getNom())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(this, summary.toString());

        // Navigate to the Equipe page and load the new equipe.
        EquipePanel equipePanel = (EquipePanel) MainWindow.getInstance().getPanel(MainWindow.EQUIPE_PANEL);
        equipePanel.loadEquipe(newEquipe);
        MainWindow.getInstance().showPanel(MainWindow.EQUIPE_PANEL);
    }
}
