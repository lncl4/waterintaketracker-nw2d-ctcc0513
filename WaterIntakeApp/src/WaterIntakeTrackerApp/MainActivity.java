package WaterIntakeTrackerApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainActivity extends JFrame {

    public MainActivity() {
        initComponents();
    }

    public void initComponents() {
        setTitle("Water Intake Tracker");
        setSize(600, 510);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new java.awt.Color(173, 216, 230)); // Light blue color

        JLabel titleLabel = new JLabel("Water Intake Tracker");
        titleLabel.setBounds(150, 50, 330, 100); // Moved closer to the center
        titleLabel.setFont(new java.awt.Font("Century Schoolbook", java.awt.Font.BOLD, 28)); // Changed font size to 36

        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(250, 200, 100, 30); // Centered horizontally
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LogIn().setVisible(true);
                dispose();
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(250, 250, 100, 30); // Centered horizontally
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUp().setVisible(true);
                dispose();
            }
        });

        add(titleLabel);
        add(loginButton);
        add(signUpButton);
    }

    public static void main(String[] args) {
        new MainActivity().setVisible(true);
    }
}
