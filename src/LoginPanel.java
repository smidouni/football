import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel for user login.
 * 
 * <p>
 * This panel provides a login form where users can enter their username and
 * password.
 * It then validates the credentials using the {@link UserDAO}.
 * </p>
 * 
 * @version 1.0
 */
public class LoginPanel extends JPanel implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel errorLabel;
    private JButton loginButton;
    private JButton goToRegisterButton;

    /**
     * Constructs a LoginPanel and initializes its UI components.
     */
    public LoginPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(800, 800));

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        // Center: form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Icon at the top
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel iconLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon("user_icon.png"); // adjust path as needed
        Image resizedImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(resizedImage));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(iconLabel, gbc);
        gbc.gridwidth = 1;

        // Username label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        formPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        formPanel.add(usernameField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        formPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        // Error label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        formPanel.add(errorLabel, gbc);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.DARK_GRAY);
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(60, 178, 43));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        buttonsPanel.add(loginButton);

        goToRegisterButton = new JButton("Register");
        goToRegisterButton.setBackground(new Color(60, 178, 43));
        goToRegisterButton.setForeground(Color.WHITE);
        goToRegisterButton.addActionListener(e -> MainWindow.getInstance().showPanel(MainWindow.REGISTER_PANEL));
        buttonsPanel.add(goToRegisterButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(buttonsPanel, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    /**
     * Handles the login button action.
     *
     * @param e the ActionEvent triggered by clicking the login button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Validate credentials using UserDAO.
        if (UserDAO.getInstance().login(username, password)) {
            errorLabel.setText("");
            // Initialize the current league and navigate to LiguePanel.
            MainWindow mainWindow = MainWindow.getInstance();
            mainWindow.setCurrentLigue(new Ligue("Ligue 1"));
            mainWindow.showPanel(MainWindow.LIGUE_PANEL);
        } else {
            errorLabel.setText("Invalid credentials, please try again.");
        }
    }
}
