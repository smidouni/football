import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel for displaying league details including teams and matches.
 * 
 * <p>
 * This panel shows lists of teams, past matches, and upcoming matches. It also
 * provides
 * buttons to create new teams, view team details, and create matches.
 * </p>
 * 
 * @version 1.0
 */
public class LiguePanel extends JPanel {
    private JList<String> equipesList;
    private JList<String> pastMatchesList;
    private JList<String> upcomingMatchesList;
    private DefaultListModel<String> equipesListModel;
    private DefaultListModel<String> pastMatchesListModel;
    private DefaultListModel<String> upcomingMatchesListModel;

    private JButton createEquipeButton;
    private JButton viewEquipeButton;
    private JButton viewMatchButton;
    private JButton disconnectButton;
    private JButton createMatchButton;

    // The current Ligue.
    private Ligue ligue;

    /**
     * Constructs a LiguePanel and initializes its UI components.
     */
    public LiguePanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(800, 800));

        // Retrieve the current Ligue.
        ligue = MainWindow.getInstance().getCurrentLigue();

        // --- Top Panel ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.DARK_GRAY);
        JLabel titleLabel = new JLabel("Ligue Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        disconnectButton = new JButton("Disconnect");
        disconnectButton.setBackground(new Color(60, 178, 43));
        disconnectButton.setForeground(Color.WHITE);
        disconnectButton.addActionListener(e -> MainWindow.getInstance().showPanel(MainWindow.LOGIN_PANEL));
        topPanel.add(disconnectButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // --- Main Content Panel ---
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(Color.DARK_GRAY);

        // Left: Equipes List
        JPanel equipesPanel = new JPanel(new BorderLayout());
        equipesPanel.setBackground(Color.DARK_GRAY);
        equipesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Equipes", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, Color.WHITE));
        equipesListModel = new DefaultListModel<>();
        if (ligue != null) {
            List<Equipe> equipes = ligue.getEquipes(); // Assumes sorted by points.
            for (Equipe equipe : equipes) {
                equipesListModel.addElement(equipe.getNom() + " - Score: " + equipe.getPoints());
            }
        }
        equipesList = new JList<>(equipesListModel);
        equipesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        equipesList.setBackground(Color.WHITE);
        equipesPanel.add(new JScrollPane(equipesList), BorderLayout.CENTER);
        contentPanel.add(equipesPanel, BorderLayout.WEST);

        // Right: Matches Panel (Past and Upcoming)
        JPanel matchesPanel = new JPanel(new BorderLayout(10, 10));
        matchesPanel.setBackground(Color.DARK_GRAY);

        // Past Matches Panel
        JPanel pastMatchesPanel = new JPanel(new BorderLayout());
        pastMatchesPanel.setBackground(Color.DARK_GRAY);
        pastMatchesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Past Matches", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, Color.WHITE));
        pastMatchesListModel = new DefaultListModel<>();
        if (ligue != null) {
            List<Match> matches = ligue.getMatches();
            for (Match match : matches) {
                if (match.getNbButsEquipe1() != -1 && match.getNbButsEquipe2() != -1) {
                    String matchInfo = match.getEquipe1().getNom() + " " + match.getNbButsEquipe1() +
                            " - " + match.getNbButsEquipe2() + " " + match.getEquipe2().getNom();
                    pastMatchesListModel.addElement(matchInfo);
                }
            }
        }
        pastMatchesList = new JList<>(pastMatchesListModel);
        pastMatchesList.setBackground(Color.WHITE);
        pastMatchesList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                // Disable visual selection.
            }
        });
        pastMatchesList.setSelectionBackground(pastMatchesList.getBackground());
        pastMatchesList.setSelectionForeground(pastMatchesList.getForeground());
        pastMatchesPanel.add(new JScrollPane(pastMatchesList), BorderLayout.CENTER);

        // Upcoming Matches Panel
        JPanel upcomingMatchesPanel = new JPanel(new BorderLayout());
        upcomingMatchesPanel.setBackground(Color.DARK_GRAY);
        upcomingMatchesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE), "Upcoming Matches", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, Color.WHITE));
        upcomingMatchesListModel = new DefaultListModel<>();
        if (ligue != null) {
            List<Match> matches = ligue.getMatches();
            for (Match match : matches) {
                if (match.getNbButsEquipe1() == -1 && match.getNbButsEquipe2() == -1) {
                    String matchInfo = match.getEquipe1().getNom() + " vs " + match.getEquipe2().getNom()
                            + " (Not played)";
                    upcomingMatchesListModel.addElement(matchInfo);
                }
            }
        }
        upcomingMatchesList = new JList<>(upcomingMatchesListModel);
        upcomingMatchesList.setBackground(Color.WHITE);
        upcomingMatchesPanel.add(new JScrollPane(upcomingMatchesList), BorderLayout.CENTER);

        // Add past and upcoming panels to the right side.
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(Color.DARK_GRAY);
        rightPanel.add(pastMatchesPanel, BorderLayout.CENTER);
        rightPanel.add(upcomingMatchesPanel, BorderLayout.SOUTH);

        contentPanel.add(rightPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // --- Bottom Panel (Navigation Buttons) ---
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setBackground(Color.DARK_GRAY);

        createEquipeButton = new JButton("Create New Equipe");
        createEquipeButton.setBackground(new Color(60, 178, 43));
        createEquipeButton.setForeground(Color.WHITE);
        createEquipeButton.addActionListener(e -> MainWindow.getInstance().showPanel(MainWindow.CREATE_EQUIPE_PANEL));
        buttonsPanel.add(createEquipeButton);

        viewEquipeButton = new JButton("View Selected Equipe");
        viewEquipeButton.setBackground(new Color(60, 178, 43));
        viewEquipeButton.setForeground(Color.WHITE);
        viewEquipeButton.addActionListener(e -> {
            if (equipesList.getSelectedIndex() != -1 && ligue != null) {
                Equipe selectedEquipe = ligue.getEquipes().get(equipesList.getSelectedIndex());
                EquipePanel equipePanel = (EquipePanel) MainWindow.getInstance().getPanel(MainWindow.EQUIPE_PANEL);
                equipePanel.loadEquipe(selectedEquipe);
                MainWindow.getInstance().showPanel(MainWindow.EQUIPE_PANEL);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an equipe from the list.");
            }
        });
        buttonsPanel.add(viewEquipeButton);

        viewMatchButton = new JButton("View Selected Match");
        viewMatchButton.setBackground(new Color(60, 178, 43));
        viewMatchButton.setForeground(Color.WHITE);
        viewMatchButton.addActionListener(e -> {
            if (!pastMatchesList.isSelectionEmpty() || !upcomingMatchesList.isSelectionEmpty()) {
                int index = -1;
                Match selectedMatch = null;
                if (upcomingMatchesList.getSelectedIndex() != -1) {
                    index = upcomingMatchesList.getSelectedIndex();
                    List<Match> upcomingMatches = new ArrayList<>();
                    for (Match match : ligue.getMatches()) {
                        if (match.getNbButsEquipe1() == -1 && match.getNbButsEquipe2() == -1) {
                            upcomingMatches.add(match);
                        }
                    }
                    if (index < upcomingMatches.size())
                        selectedMatch = upcomingMatches.get(index);
                } else if (pastMatchesList.getSelectedIndex() != -1) {
                    index = pastMatchesList.getSelectedIndex();
                    List<Match> pastMatches = new ArrayList<>();
                    for (Match match : ligue.getMatches()) {
                        if (match.getNbButsEquipe1() != -1 && match.getNbButsEquipe2() != -1) {
                            pastMatches.add(match);
                        }
                    }
                    if (index < pastMatches.size())
                        selectedMatch = pastMatches.get(index);
                }
                if (selectedMatch != null) {
                    MatchPanel matchPanel = (MatchPanel) MainWindow.getInstance().getPanel(MainWindow.MATCH_PANEL);
                    matchPanel.loadMatch(selectedMatch);
                    MainWindow.getInstance().showPanel(MainWindow.MATCH_PANEL);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a match from the list.");
            }
        });
        buttonsPanel.add(viewMatchButton);

        createMatchButton = new JButton("Create Match");
        createMatchButton.setBackground(new Color(60, 178, 43));
        createMatchButton.setForeground(Color.WHITE);
        createMatchButton.addActionListener(e -> {
            if (ligue == null || ligue.getEquipes().size() < 2) {
                JOptionPane.showMessageDialog(this, "Not enough teams to create a match.");
                return;
            }
            List<Equipe> equipes = ligue.getEquipes();
            JComboBox<String> team1Combo = new JComboBox<>();
            JComboBox<String> team2Combo = new JComboBox<>();
            for (Equipe eq : equipes) {
                team1Combo.addItem(eq.getNom());
                team2Combo.addItem(eq.getNom());
            }
            JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
            panel.add(new JLabel("Team 1:"));
            panel.add(team1Combo);
            panel.add(new JLabel("Team 2:"));
            panel.add(team2Combo);
            int result = JOptionPane.showConfirmDialog(this, panel, "Select Teams for the Match",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                if (team1Combo.getSelectedIndex() == team2Combo.getSelectedIndex()) {
                    JOptionPane.showMessageDialog(this, "Please select two different teams.");
                    return;
                }
                Equipe equipe1 = equipes.get(team1Combo.getSelectedIndex());
                Equipe equipe2 = equipes.get(team2Combo.getSelectedIndex());
                Match newMatch = Match.creerMatch(equipe1, equipe2);
                JOptionPane.showMessageDialog(this,
                        "Match created between " + equipe1.getNom() + " and " + equipe2.getNom());
                // Refresh the league data.
                MainWindow.getInstance().setCurrentLigue(new Ligue(ligue.getNom()));
                updateContent();
            }
        });
        buttonsPanel.add(createMatchButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Refreshes the content of the panel using the current Ligue data.
     */
    public void updateContent() {
        ligue = MainWindow.getInstance().getCurrentLigue();
        if (ligue == null) {
            JOptionPane.showMessageDialog(this, "No league data available.");
            return;
        }
        // Update Equipes list.
        equipesListModel.clear();
        List<Equipe> equipes = ligue.getEquipes();
        for (Equipe equipe : equipes) {
            equipesListModel.addElement(equipe.getNom() + " - Score: " + equipe.getPoints());
        }
        // Update Matches lists.
        pastMatchesListModel.clear();
        upcomingMatchesListModel.clear();
        List<Match> matches = ligue.getMatches();
        for (Match match : matches) {
            if (match.getNbButsEquipe1() != -1 && match.getNbButsEquipe2() != -1) {
                String matchInfo = match.getEquipe1().getNom() + " " + match.getNbButsEquipe1() +
                        " - " + match.getNbButsEquipe2() + " " + match.getEquipe2().getNom();
                pastMatchesListModel.addElement(matchInfo);
            } else if (match.getNbButsEquipe1() == -1 && match.getNbButsEquipe2() == -1) {
                String matchInfo = match.getEquipe1().getNom() + " vs " + match.getEquipe2().getNom() + " (Not played)";
                upcomingMatchesListModel.addElement(matchInfo);
            }
        }
    }
}
