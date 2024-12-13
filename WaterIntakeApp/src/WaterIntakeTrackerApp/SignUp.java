package WaterIntakeTrackerApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SignUp extends javax.swing.JFrame {

    private JTextField suusernameField;
    private JPasswordField supasswordField;
    private JPasswordField suretypepasswordField;
    private JButton sucancelButton;
    private JButton suSignUpButton;

    public SignUp() {
        initComponents();
    }

    public void initComponents() {
        setTitle("Sign Up");
        setSize(610, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new java.awt.Color(173, 216, 230));

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new java.awt.Font("Century Schoolbook", java.awt.Font.BOLD, 28));
        titleLabel.setBounds(230, 50, 150, 50); // Centered at top

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(160, 150, 80, 25);
        suusernameField = new javax.swing.JTextField();
        suusernameField.setBounds(260, 150, 200, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(160, 190, 80, 25);
        supasswordField = new javax.swing.JPasswordField();
        supasswordField.setBounds(260, 190, 200, 25);

        JLabel passwordLabel2 = new JLabel("Re-enter Password:");
        passwordLabel2.setBounds(160, 230, 150, 25);
        suretypepasswordField = new javax.swing.JPasswordField();
        suretypepasswordField.setBounds(280, 230, 180, 25);

        sucancelButton = new javax.swing.JButton("Cancel");
        sucancelButton.setBounds(200, 290, 80, 30);
        sucancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainActivity().setVisible(true);
                dispose();
            }
        });

        suSignUpButton = new javax.swing.JButton("Sign Up");
        suSignUpButton.setBounds(320, 290, 80, 30);
        suSignUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = suusernameField.getText();
                String password = String.valueOf(supasswordField.getPassword());
                String retypepassword = String.valueOf(suretypepasswordField.getPassword());
                if (password.equals(retypepassword)) {
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/waterintake", "root", "password");
                        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, username);
                        pstmt.setString(2, password);
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Sign Up successful.");
                        new LogIn().setVisible(true);
                        dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error connecting to the database: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Passwords do not match.");
                }
            }
        });

        add(titleLabel);
        add(usernameLabel);
        add(suusernameField);
        add(passwordLabel);
        add(supasswordField);
        add(sucancelButton);
        add(suSignUpButton);
        add(passwordLabel2);
        add(suretypepasswordField);
    }
}
