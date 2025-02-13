import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterPanel extends JPanel implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel errorLabel;
    private JButton registerButton;
    private JButton goToLoginButton;

    public RegisterPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(800, 800));

        JLabel titleLabel = new JLabel("Register", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

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
        ImageIcon originalIcon = new ImageIcon("user_icon.png");
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

        // Confirm password label and field
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        confirmPassLabel.setForeground(Color.WHITE);
        formPanel.add(confirmPassLabel, gbc);

        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        formPanel.add(confirmPasswordField, gbc);

        // Error label
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        formPanel.add(errorLabel, gbc);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.DARK_GRAY);
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(60, 178, 43));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(this);
        buttonsPanel.add(registerButton);

        goToLoginButton = new JButton("Back to Login");
        goToLoginButton.setBackground(new Color(60, 178, 43));
        goToLoginButton.setForeground(Color.WHITE);
        goToLoginButton.addActionListener(e -> MainWindow.getInstance().showPanel(MainWindow.LOGIN_PANEL));
        buttonsPanel.add(goToLoginButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(buttonsPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Reset fields when the panel becomes visible
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                usernameField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");
                errorLabel.setText("");
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty() || !password.equals(confirmPassword)) {
            errorLabel.setText("Please fill all fields correctly.");
        } else {
            errorLabel.setText("");
            UserDAO.getInstance().register(username, password);
            // Set the current league and navigate to LiguePanel.
            MainWindow mainWindow = MainWindow.getInstance();
            mainWindow.setCurrentLigue(new Ligue("Ligue 1"));
            mainWindow.showPanel(MainWindow.LIGUE_PANEL);
        }
    }
}
