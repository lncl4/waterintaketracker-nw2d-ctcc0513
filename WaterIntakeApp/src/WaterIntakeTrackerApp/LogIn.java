package WaterIntakeTrackerApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LogIn extends javax.swing.JFrame {

    private javax.swing.JTextField usernameField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton logInButton;

    public LogIn() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Log In");
        setSize(610, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new java.awt.Color(173, 216, 230));

        JLabel titleLabel = new JLabel("Log In");
        titleLabel.setFont(new java.awt.Font("Century Schoolbook", java.awt.Font.BOLD, 28));
        titleLabel.setBounds(230, 50, 150, 50); // Centered at top

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(160, 150, 80, 25);
        usernameField = new javax.swing.JTextField();
        usernameField.setBounds(260, 150, 200, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(160, 190, 80, 25);
        passwordField = new javax.swing.JPasswordField();
        passwordField.setBounds(260, 190, 200, 25);

        cancelButton = new javax.swing.JButton("Cancel");
        cancelButton.setBounds(200, 250, 80, 30);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainActivity().setVisible(true);
                dispose();
            }
        });

        logInButton = new javax.swing.JButton("Log In");
        logInButton.setBounds(320, 250, 80, 30);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (verifyCredentials(username, password)) {
                    JOptionPane.showMessageDialog(null, "Log In successful.");
                    new WaterIntake().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Try again.");
                }
            }
        });

        add(titleLabel);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(cancelButton);
        add(logInButton);
    }

    private boolean verifyCredentials(String username, String password) {
        boolean isValid = false;
        String url = "jdbc:mysql://127.0.0.1:3306/waterintake"; // Update with your database URL
        String dbUser = "root"; // Update with your database username
        String dbPassword = "password"; // Update with your database password
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isValid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }

        return isValid;
    }
}
