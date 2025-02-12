import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel for displaying and simulating match details.
 * 
 * <p>
 * This panel shows the teams involved in a match, the current score, and the
 * match status.
 * It also provides a button to simulate the match.
 * </p>
 * 
 * @version 1.0
 */
public class MatchPanel extends JPanel {
    private JLabel team1Label, team2Label, scoreLabel, statusLabel;
    private JButton simulateButton, backButton;
    private Match currentMatch; // the match to display

    /**
     * Constructs a MatchPanel and initializes its UI components.
     */
    public MatchPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(800, 800));

        // Title at the top
        JLabel titleLabel = new JLabel("Match Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Center wrapper panel
        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setBackground(Color.DARK_GRAY);
        centerWrapper.add(Box.createVerticalGlue());

        // Match details panel
        JPanel matchDetailsPanel = new JPanel();
        matchDetailsPanel.setBackground(new Color(50, 50, 50));
        matchDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        matchDetailsPanel.setLayout(new BoxLayout(matchDetailsPanel, BoxLayout.Y_AXIS));
        matchDetailsPanel.setMaximumSize(new Dimension(600, 300));
        matchDetailsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Teams panel
        JPanel teamsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        teamsPanel.setBackground(new Color(50, 50, 50));
        team1Label = new JLabel("Team 1");
        team1Label.setFont(new Font("SansSerif", Font.BOLD, 28));
        team1Label.setForeground(Color.WHITE);
        JLabel vsLabel = new JLabel("VS");
        vsLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        vsLabel.setForeground(Color.WHITE);
        team2Label = new JLabel("Team 2");
        team2Label.setFont(new Font("SansSerif", Font.BOLD, 28));
        team2Label.setForeground(Color.WHITE);
        teamsPanel.add(team1Label);
        teamsPanel.add(vsLabel);
        teamsPanel.add(team2Label);
        matchDetailsPanel.add(teamsPanel);

        matchDetailsPanel.add(Box.createVerticalStrut(20));

        // Score label
        scoreLabel = new JLabel("Score: ", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        matchDetailsPanel.add(scoreLabel);

        matchDetailsPanel.add(Box.createVerticalStrut(10));

        // Status label
        statusLabel = new JLabel("Status: ", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        matchDetailsPanel.add(statusLabel);

        centerWrapper.add(matchDetailsPanel);
        centerWrapper.add(Box.createVerticalGlue());
        add(centerWrapper, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.DARK_GRAY);
        simulateButton = new JButton("Simulate Match");
        simulateButton.setBackground(new Color(60, 178, 43));
        simulateButton.setForeground(Color.WHITE);
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMatch != null &&
                        (currentMatch.getNbButsEquipe1() == -1 || currentMatch.getNbButsEquipe2() == -1)) {
                    currentMatch.simulerMatch();
                    currentMatch.saveMatch();
                    updateMatchDetails();
                }
            }
        });
        bottomPanel.add(simulateButton);

        backButton = new JButton("Back to Ligue Panel");
        backButton.setBackground(new Color(60, 178, 43));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> MainWindow.getInstance().showPanel(MainWindow.LIGUE_PANEL));
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Loads a match into the panel and updates the displayed details.
     *
     * @param match the {@link Match} to load.
     */
    public void loadMatch(Match match) {
        this.currentMatch = match;
        updateMatchDetails();
    }

    /**
     * Updates the UI components based on the current match data.
     */
    private void updateMatchDetails() {
        if (currentMatch != null) {
            team1Label.setText(currentMatch.getEquipe1().getNom());
            team2Label.setText(currentMatch.getEquipe2().getNom());
            int score1 = currentMatch.getNbButsEquipe1();
            int score2 = currentMatch.getNbButsEquipe2();
            if (score1 == -1 || score2 == -1) {
                scoreLabel.setText("Score: Not played yet");
                statusLabel.setText("Status: Upcoming");
                simulateButton.setEnabled(true);
            } else {
                scoreLabel.setText("Score: " + score1 + " - " + score2);
                statusLabel.setText("Status: Finished");
                simulateButton.setEnabled(false);
            }
        }
    }
}
